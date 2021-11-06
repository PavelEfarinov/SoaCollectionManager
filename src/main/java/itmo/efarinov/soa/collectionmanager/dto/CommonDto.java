package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.utils.JsonableModel;

public abstract class CommonDto<T extends JsonableModel> {
    public abstract T toModel();
}
