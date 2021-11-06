package itmo.efarinov.soa.collectionmanager.utils;


import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;

public abstract class JsonableModel {
    public String toJson() {
        return GsonObjectMapper.Mapper.toJson(this);
    }
}
