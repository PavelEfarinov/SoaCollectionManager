package itmo.efarinov.soa.crudservice.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import static org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS;

public final class JacksonFeature implements Feature {

    private static final ObjectMapper MAPPER;

    static {

        // Create the new object mapper.
        MAPPER = new ObjectMapper();

        // Enable/disable various configuration flags.
        MAPPER.configure(
                DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        // StdDateFormat is ISO8601 since jackson 2.9
        MAPPER.setDateFormat(new StdDateFormat().withColonInTimeZone(false));
    }

    @Override
    public boolean configure(final FeatureContext context) {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider(
                MAPPER, DEFAULT_ANNOTATIONS);
        context.register(provider);

        return true;
    }
}
