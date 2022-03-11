package itmo.efarinov.soa.json;


import itmo.efarinov.soa.json.gson.GsonObjectMapper;

import java.io.Serializable;

public abstract class JsonableModel implements Serializable {
    public String toJson() {
        return GsonObjectMapper.Mapper.toJson(this);
    }
}
