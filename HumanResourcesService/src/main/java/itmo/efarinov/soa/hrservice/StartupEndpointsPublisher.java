package itmo.efarinov.soa.hrservice;


import itmo.efarinov.soa.hr.interfaces.IHrService;
import itmo.efarinov.soa.hrservice.controller.HrSoapController;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.xml.ws.Endpoint;
import lombok.SneakyThrows;

@Startup
@Singleton
public class StartupEndpointsPublisher {
    Endpoint publishedEndpoint;

    @EJB
    IHrService hrService;

    @SneakyThrows
    @PostConstruct
    public void init() {
        // Perform action during application's startup
        publishedEndpoint = Endpoint.publish("http://localhost:12345/hr/api",
                new HrSoapController(hrService));
    }

    @PreDestroy
    public void shutdown()
    {
        if(publishedEndpoint != null && publishedEndpoint.isPublished()) {
            publishedEndpoint.stop();
            publishedEndpoint = null;
        }
    }
}
