package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.utils.CommonControllerHelper;
import itmo.efarinov.soa.crudservice.interfaces.utils.ICommonControllerHelper;
import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.crudservice.error.BadRequestException;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import itmo.efarinov.soa.crudservice.filter.SortingOrderType;
import itmo.efarinov.soa.crudservice.interfaces.filter.IEntityFilterMapper;
import itmo.efarinov.soa.crudservice.interfaces.mapper.ICommonEntityMapper;
import itmo.efarinov.soa.crudservice.interfaces.repository.ICrudRepository;
import itmo.efarinov.soa.dto.CommonDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

import javax.validation.Valid;
import java.util.List;

public abstract class CommonCrudController<
        T extends CommonEntity<DT>,
        DT extends CommonDto,
        RT extends ICrudRepository<T>,
        EMT extends ICommonEntityMapper<T, DT>,
        EFMT extends IEntityFilterMapper<T>> {

    protected ICommonControllerHelper controllerHelper;

    protected RT entityRepository;
    protected EMT entityMapper;

    protected void init(RT entityRepository, EMT entityMapper, EFMT filterMapper)
    {
        this.entityRepository = entityRepository;
        this.entityMapper = entityMapper;
        controllerHelper = new CommonControllerHelper<>(filterMapper);
    }

    @GET
    @Path("/count")
    @SneakyThrows
    @Produces(MediaType.TEXT_PLAIN)
    public Response doGetCountMethod(@QueryParam("filters") String filtersParam) {
        return doGetCount(filtersParam);
    }

    @DELETE
    @Path("/{id}")
    public Response doDeleteMethod(@PathParam("id") Integer id) {
        return doDelete(id);
    }

    @POST
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPostMethod(@Valid DT postArgs) {
        return doPost(postArgs);
    }

    @PUT
    @Path("/{id}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPutMethod(@PathParam("id") Integer id, DT putArgs) {
        return doPut(id, putArgs);
    }

    @GET
    @Path("/{id}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSingleItemMethod(@PathParam("id") Integer id) {
        return Response.ok().entity(entityRepository.getById(id)).build();
    }

    @GET
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCollectionMethod(@QueryParam("page") Integer page,
                                        @QueryParam("pageSize") Integer pageSize,
                                        @QueryParam("sort") String orderByParam,
                                        @QueryParam("filters") String filterParam) {
        return getCollection(page, pageSize, orderByParam, filterParam);
    }

    @SneakyThrows
    @Produces(MediaType.TEXT_PLAIN)
    protected Response doGetCount(String filtersParam) {
        return Response.ok().entity(entityRepository.countByFilter(controllerHelper.getFilters(filtersParam))).build();
    }

    protected Response doDelete(Integer id) {
        entityRepository.deleteById(id);
        return Response.ok().build();
    }

    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    protected Response doPost(DT postArgs) {
        T entity = entityMapper.toModel(postArgs);
        entityRepository.save(entity);
        return Response.ok().entity(entity).build();
    }

    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    protected Response doPut(Integer id, DT putArgs) {
        T entity = entityMapper.toModel(putArgs);
        entity.setId(id);
        entityRepository.update(entity);
        return Response.ok().entity(entity).build();
    }

    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    protected Response getSingleItem(Integer id) {
        return Response.ok().entity(entityRepository.getById(id)).build();
    }

    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    protected Response getCollection(Integer page,
                                     Integer pageSize,
                                     String orderByParam,
                                     String filterParam) {
        if (page != null) {
            if (page < 0) {
                throw new BadRequestException("Page number cannot be negative!");
            }
        } else {
            page = 0;
        }
        if (pageSize != null) {
            if (pageSize < 0) {
                throw new BadRequestException("Page size cannot be negative!");
            }
        } else {
            pageSize = 10;
        }
        SortingOrder orderBy = controllerHelper.getSortingOrder(orderByParam);
        if (orderBy == null) {
            orderBy = SortingOrder.builder().fieldName("id").type(SortingOrderType.ASC).build();
        }

        List<T> list = entityRepository.getByFilter(controllerHelper.getFilters(filterParam), pageSize, page, orderBy);
        return Response.ok().entity(list).build();
    }
}
