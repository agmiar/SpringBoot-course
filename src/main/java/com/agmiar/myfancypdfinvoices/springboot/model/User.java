package com.agmiar.myfancypdfinvoices.springboot.model;

import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.helper.JsonRenderable;
//import com.fasterxml.jackson.databind.JsonNode;

public final class User implements JsonRenderable {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean sameId(String id) {
        return this.id.equals(id);
    }

    @Override
    public FluidJson toJson() {
        return FluidJson.rootObject()
                .put("id", id)
                .put("name", name)
                .build();
    }
}
