package itmo.efarinov.soa.collectionmanager.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class GsonObjectMapper {
    public static Gson Mapper = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapterSerializer())
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapterDeserializer())
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapterSerializer())
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapterDeserializer())
            .create();
}
