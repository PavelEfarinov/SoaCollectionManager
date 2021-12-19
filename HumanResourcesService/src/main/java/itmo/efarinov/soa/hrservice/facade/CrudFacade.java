package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.ErrorDto;
import itmo.efarinov.soa.hrservice.exceptions.NestedRequestException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class CrudFacade<T, DT> {
    private String pathSuffix;
    private Class<T> runtimeClass;

    public CrudFacade(String path, Class<T> clazz)
    {
        runtimeClass = clazz;
        pathSuffix = path;
    }

    private WebTarget getTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/app/api/");
    }

    private WebTarget getEntityTarget() {
        return getTarget().path(pathSuffix);
    }

    public T getById(int id) throws NestedRequestException {
        WebTarget wt = getEntityTarget();
        Response response = wt.path(id + "").request(MediaType.APPLICATION_JSON).get();
        if(hasError(response))
        {
            throw new NestedRequestException(this.getClass().getSimpleName() + " error. " + response.readEntity(ErrorDto.class).message);
        }
        return response.readEntity(runtimeClass);
    }

    public void updateById(int id, DT newEntity) throws NestedRequestException {
        WebTarget wt = getEntityTarget();
        Response response = wt.path(id + "").request(MediaType.APPLICATION_JSON).put(Entity.json(newEntity));
        if(hasError(response))
        {
            throw new NestedRequestException(response.readEntity(ErrorDto.class).message);
        }
    }

    protected boolean hasError(Response response) {
        try {
            response.bufferEntity();
            ErrorDto error = response.readEntity(ErrorDto.class);
            return error != null;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
