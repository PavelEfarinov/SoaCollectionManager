package itmo.efarinov.soa.json;


import itmo.efarinov.soa.json.gson.GsonObjectMapper;

public abstract class JsonableModel {
    public String toJson() {
        return GsonObjectMapper.Mapper.toJson(this);
    }
}
