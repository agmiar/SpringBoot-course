package com.agmiar.myfancypdfinvoices.springboot.helper;

import com.fasterxml.jackson.annotation.JsonValue;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.ArrayNode;
import java.util.Collection;

public final class FluidJson {
    private static ObjectMapper mapper;
    private final JsonNode node;
    private final FluidJson parent;

    static void setMapper(ObjectMapper objectMapper) {
        if (mapper == null) {
            mapper = objectMapper;
        }
    }

    public FluidJson(JsonNode node) {
        this.node = node;
        this.parent = null;
    }

    private FluidJson(JsonNode node, FluidJson parent) {
        this.node = node;
        this.parent = parent;
    }

    // Factory

    /**
     * Crea el objeto base
     */
    public static FluidJson rootObject() {
        return new FluidJson(mapper.createObjectNode(), null);
    }

    /**
     * Crea el array base
     * @return []
     */
    public static FluidJson rootArray() {
        return new FluidJson(mapper.createArrayNode(), null);
    }

    // Métodos para OBJETOS

    /**
     * @param key nombre de la propiedad
     * @param value valor de la propiedad
     * @return Agrega un par clave-valor al objeto a crear
     */
    public FluidJson put(String key, String value) {
        asegurarObjeto();
        ((ObjectNode) node).put(key, value);
        return this;
    }

    /**
     * @param key nombre de la propiedad
     * @param value valor de la propiedad
     * @return Agrega un par clave-valor al objeto a crear
     */
    public FluidJson put(String key, Integer value) {
        asegurarObjeto();
        ((ObjectNode) node).put(key, value);
        return this;
    }

    /**
     * @param key nombre de la propiedad
     * @param value valor de la propiedad
     * @return Agrega un par clave-valor al objeto a crear
     */
    public FluidJson put(String key, boolean value) {
        asegurarObjeto();
        ((ObjectNode) node).put(key, value);
        return this;
    }

    /**
     * @param key nombre del campo que contiene al nuevo objeto JSON anidado
     * @return objeto JSON anidado -> e.j. {"key": {}}
     */
    public FluidJson object(String key) {
        asegurarObjeto();
        ObjectNode child = mapper.createObjectNode();
        ((ObjectNode) node).set(key, child);
        return new FluidJson(child, this);
    }

    /**
     * @param key nombre del campo que contiene al nuevo array anidado
     * @return array anidado dentro de objeto JSON -> e.j. {"key": []}
     */
    public FluidJson array(String key) {
        asegurarObjeto();
        ArrayNode child = mapper.createArrayNode();
        ((ObjectNode) node).set(key, child);
        return new FluidJson(child, this);
    }

    // Métodos para ARRAYS

    /**
     * @return [value] -> e.j. ["Hola"]
     */
    public FluidJson value(String value) {
        asegurarArray();
        ((ArrayNode) node).add(value);
        return this;
    }

    /**
     * @return [value] -> e.j. [100]
     */
    public FluidJson value(Integer value) {
        asegurarArray();
        ((ArrayNode) node).add(value);
        return this;
    }

    /**
     * @return [value] -> e.j. [true]
     */
    public FluidJson value(boolean value) {
        asegurarArray();
        ((ArrayNode) node).add(value);
        return this;
    }

    /**
     * @return Contexto de un nuevo objeto JSON a construir
     */
    public FluidJson object() {
        asegurarArray();
        ObjectNode obj = mapper.createObjectNode();
        ((ArrayNode) node).add(obj);
        return new FluidJson(obj, this);
    }

    /**
     * @return Array anidado - [[]]
     */
    public FluidJson array() {
        asegurarArray();
        ArrayNode arr = mapper.createArrayNode();
        ((ArrayNode) node).add(arr);
        return new FluidJson(arr, this);
    }

    /** IDEAL PARA OBJETOS QUE YA ESTAN EN FORMATO FLUID-JSON
     * Agrega un objeto completo al JSON array
     * @return [object] -> e.j. [{"key": value}]
     */
    public FluidJson putObject(FluidJson fluentJson) {
        asegurarArray();
        ((ArrayNode) node).add(fluentJson.node);
        return this;
    }

    /** SOLO VALIDO PARA POJOs
     * -> Agrega un objeto completo al JSON array
     * @return [object] -> e.j. [{"key": value}]
     */
    public FluidJson putObject(Object object) {
        asegurarArray();
        ((ArrayNode) node).add(mapper.valueToTree(object));
        return this;
    }


    // Construcción y salida

    /**
     * Solo usarlo para objetos/arrays anidados.
     * Finaliza la construcción del objeto actual y vuelve al contexto del objeto/array padre
     * @return objeto actual terminado
     */
    public FluidJson build() {
        return parent != null ? parent : this;
    }

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(getRoot().node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FluidJson getRoot() {
        FluidJson current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    // Safeguards

    private void asegurarObjeto() {
        if (!(node instanceof ObjectNode)) {
            throw new IllegalStateException("El nodo actual no es un objeto");
        }
    }

    private void asegurarArray() {
        if (!(node instanceof ArrayNode)) {
            throw new IllegalStateException("El nodo actual no es un array");
        }
    }

    // convertir Collection a FluidJson
    public static FluidJson convertCollectionToJson(Iterable<? extends JsonRenderable> lista){
        var json = FluidJson.rootArray();
        for (JsonRenderable obj : lista) {
            json.putObject(obj.toJson());
        }
        return json;
    }

    /**
     * Punto de integración entre Jackson y Spring Boot
     * - Le dice a Jackson cómo serializar FluidJSON a JSON puro
     */
    @JsonValue // este tag es propio de Jackson
    public JsonNode jsonValue() {
        return getRoot().node;
    }
}



