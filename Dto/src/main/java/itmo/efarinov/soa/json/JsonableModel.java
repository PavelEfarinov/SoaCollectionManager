package itmo.efarinov.soa.json;


import com.fasterxml.jackson.annotation.JsonIgnore;
import itmo.efarinov.soa.json.gson.GsonObjectMapper;

public abstract class JsonableModel {
    public String toJson() {
        return GsonObjectMapper.Mapper.toJson(this);
    }
}
