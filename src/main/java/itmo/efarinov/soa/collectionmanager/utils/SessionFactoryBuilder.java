package itmo.efarinov.soa.collectionmanager.utils;

import itmo.efarinov.soa.collectionmanager.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Optional;
import java.util.Properties;

public class SessionFactoryBuilder {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        try {

            Configuration configuration = new Configuration();
            Properties settings = new Properties();

            settings.put(Environment.DRIVER, "org.postgresql.Driver");
            settings.put(Environment.URL, String.format("jdbc:postgresql://%s:%s/%s",
                    Optional.ofNullable(System.getenv("DB_HOST"))
                            .orElseThrow(() -> new IllegalArgumentException("Missing required env variable DB_HOST")),
                    Optional.ofNullable(System.getenv("DB_PORT"))
                            .orElseThrow(() -> new IllegalArgumentException("Missing required env variable DB_PORT")),
                    Optional.ofNullable(System.getenv("DB_NAME"))
                            .orElseThrow(() -> new IllegalArgumentException("Missing required env variable DB_NAME"))
            ));
            settings.put(Environment.USER,
                    Optional.ofNullable(System.getenv("DB_USER"))
                            .orElseThrow(() -> new IllegalArgumentException("Missing required env variable DB_USER")));
            settings.put(Environment.PASS,
                    Optional.ofNullable(System.getenv("DB_PASS"))
                            .orElseThrow(() -> new IllegalArgumentException("Missing required env variable DB_PASS")));
            settings.put(Environment.SHOW_SQL,
                    Optional.ofNullable(System.getenv("DB_SHOW_SQL"))
                            .orElse("true"));
            settings.put(Environment.USE_SQL_COMMENTS,
                    Optional.ofNullable(System.getenv("DB_USE_SQL_COMMENTS"))
                            .orElse("true"));
            settings.put(Environment.HBM2DDL_AUTO, "update");
            settings.put(Environment.AUTOCOMMIT, "true");
            settings.put(Environment.JDBC_TIME_ZONE, "UTC");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(WorkerEntity.class);
            configuration.addAnnotatedClass(CoordinatesEntity.class);
            configuration.addAnnotatedClass(OrganizationEntity.class);
            configuration.addAnnotatedClass(Position.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
