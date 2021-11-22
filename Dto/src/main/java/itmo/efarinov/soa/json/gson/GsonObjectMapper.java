package itmo.efarinov.soa.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import java.lang.reflect.Type;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class GsonObjectMapper {
    @SneakyThrows
    public static <T> T fromJson(BufferedReader s, Class<T> runtimeClass){
        try {
            return Mapper.fromJson(s, runtimeClass);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Could not parse given object due to " + e.getMessage());
        }
    }

    @SneakyThrows
    public static <T> T fromJson(String s, Type runtimeClass){
        try {
            return Mapper.fromJson(s, runtimeClass);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Could not parse given object due to " + e.getMessage());
        }
    }

    public static Gson Mapper = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapterSerializer())
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapterDeserializer())
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapterSerializer())
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapterDeserializer())
            .create();
}
