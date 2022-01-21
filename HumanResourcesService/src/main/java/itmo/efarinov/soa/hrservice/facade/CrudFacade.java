package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.ErrorDto;
import itmo.efarinov.soa.hrservice.exceptions.NestedRequestException;
import lombok.SneakyThrows;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.FileInputStream;
import java.security.KeyStore;

public abstract class CrudFacade<T, DT> {
    private final String pathSuffix;
    private final Class<T> runtimeClass;

    public CrudFacade(String path, Class<T> clazz) {
        runtimeClass = clazz;
        pathSuffix = path;
    }

    @SneakyThrows
    private WebTarget getTarget() {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){
                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        return true;
                    }
                });
        String keystoreLocation = System.getenv("KEYSTORE_PATH");
        String keystorePassword = System.getenv("KEYSTORE_PASS");
        System.out.println("KEYSTORE_PATH = " + keystoreLocation);
        System.out.println("KEYSTORE_PASS = " + keystorePassword);
        FileInputStream is = new FileInputStream(keystoreLocation);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, keystorePassword.toCharArray());
        Client client = ClientBuilder.newBuilder().trustStore(keystore).build();
        return client.target(System.getenv("APP_URL"));
    }

    private WebTarget getEntityTarget() {
        return getTarget().path(pathSuffix);
    }

    public T getById(int id) throws NestedRequestException {
        WebTarget wt = getEntityTarget();
        Response response = wt.path(id + "").request(MediaType.APPLICATION_JSON).get();
        if (hasError(response)) {
            throw new NestedRequestException(this.getClass().getSimpleName() + " error. " + response.readEntity(ErrorDto.class).message);
        }
        return response.readEntity(runtimeClass);
    }

    public T updateById(int id, DT newEntity) throws NestedRequestException {
        WebTarget wt = getEntityTarget();
        Response response = wt.path(id + "").request(MediaType.APPLICATION_JSON).put(Entity.json(newEntity));
        if (hasError(response)) {
            throw new NestedRequestException(response.readEntity(ErrorDto.class).message);
        }
        return response.readEntity(runtimeClass);
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
