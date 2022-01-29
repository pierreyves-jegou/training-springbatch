package com.example.batch.service;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
//@Scope("job")
public class ResourceFollower {

    public enum STATUS { OK, KO }

    Map<Resource, STATUS> resourcesStatusMap = new HashMap<>();

    public void putAKO(Resource resource){
        resourcesStatusMap.put(resource, STATUS.KO);
    }

    public Map<Resource, STATUS> getResourcesStatusMap(){
        return this.resourcesStatusMap;
    }


}
