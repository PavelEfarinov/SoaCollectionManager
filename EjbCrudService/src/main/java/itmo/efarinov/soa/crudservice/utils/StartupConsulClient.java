package itmo.efarinov.soa.crudservice.utils;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.*;
import lombok.SneakyThrows;

import java.util.Collections;

@Startup
@Singleton
public class StartupConsulClient {
    private AgentClient agentClient = null;
    private final String serviceId = "crud";

    @SneakyThrows
    @PostConstruct
    public void init() {
        // Perform action during application's startup
        Consul client = Consul.builder().build();
        agentClient = client.agentClient();

        ImmutableRegistration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name("crud-service")
                .port(8181)
                .check(Registration.RegCheck.ttl(3L)) // registers with a TTL of 3 seconds
                .tags(Collections.singletonList("crud"))
                .meta(Collections.singletonMap("version", "1.0"))
                .build();

        agentClient.register(service);
        agentClient.pass(serviceId);
    }

    @SneakyThrows
    @Lock(LockType.WRITE)
    @Schedule(second = "*/2",minute = "*", hour = "*")
    public void HeartBeat() {
        agentClient.pass(serviceId);
    }

    @PreDestroy
    public void destroy() {
        agentClient.deregister(serviceId);
        // Perform action during application's shutdown
    }
}
