package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.crudservice.error.BadRequestException;
import itmo.efarinov.soa.crudservice.filter.CommonEntityFilterMapper;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import itmo.efarinov.soa.crudservice.filter.SortingOrderType;
import itmo.efarinov.soa.crudservice.mapper.CommonEntityMapper;
import itmo.efarinov.soa.crudservice.repository.CommonCrudRepository;
import itmo.efarinov.soa.crudservice.controller.CommonApplicationServlet;
import itmo.efarinov.soa.dto.CommonDto;
import lombok.SneakyThrows;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class CommonCrudServlet<T extends CommonEntity<DT>, DT extends CommonDto> extends CommonApplicationServlet<T> {

    protected CommonCrudRepository<T> entityRepository;
    protected CommonEntityMapper<T, DT> entityMapper;

    protected CommonCrudServlet(CommonEntityFilterMapper<T> entityFilterMapper, CommonCrudRepository<T> repository, CommonEntityMapper<T, DT> entityMapper) {
        super(entityFilterMapper);
        entityRepository = repository;
        this.entityMapper = entityMapper;
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
    public Response doPostMethod(DT postArgs) {
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
        return Response.ok().entity(entityRepository.countByFilter(getFilters(filtersParam))).build();
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
        SortingOrder orderBy = getSortingOrder(orderByParam);
        if (orderBy == null) {
            orderBy = SortingOrder.builder().fieldName("id").type(SortingOrderType.ASC).build();
        }

        List<T> list = entityRepository.getByFilter(getFilters(filterParam), pageSize, page, orderBy);
        return Response.ok().entity(list).build();
    }
}
