package itmo.efarinov.soa.hrservice.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.Node;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;
import jakarta.ejb.Singleton;

import java.util.List;

@Singleton
public class ServiceConnector {
    public String DiscoverRunningCrudService() {
        Consul client = Consul.builder().build();
        HealthClient healthClient = client.healthClient();
        // Discover only "passing" nodes
        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("crud-service").getResponse();
        Service firstService = nodes.get(0).getService();
        Node node = nodes.get(0).getNode();
        String address = node.getAddress();
        int port = firstService.getPort();
        System.out.println("Found crud service at: " + address + ":" + port);
        return "https://" + address + ":" + port + "/app/api/";
    }
}
