package com.mllq.base.proyect_base.core.commons.libs.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // <-- esta línea
    // DeviceMapper para deserialización (entrada) - acepta camelCase Y snake_case
    private static final ObjectMapper deserializerMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // <-- agrega esto
    // DeviceMapper para serialización (salida) - produce snake_case
    private static final ObjectMapper serializerMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    public static <T> Set<T> toSet(String json, TypeReference<Set<T>> typeReference) {
        if (json == null || json.isEmpty()) {
            return Set.of(); // Retorna una lista vacía si el JSON es nulo o vacío
        }

        try {
            return deserializerMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("Error al convertir el JSON a una lista de sets de objetos: {}", e.getMessage());
            return Set.of(); // Retorna una lista vacía si ocurre un errorTcp
        }
    }
    public static <T> Set<T> toSet(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return Set.of();
        }

        try {
            JavaType type = deserializerMapper.getTypeFactory().constructCollectionType(Set.class, clazz);
            return deserializerMapper.readValue(json, type);
        } catch (Exception e) {
            log.warn("Error al convertir el JSON a un Set de objetos: {}", e.getMessage());
            return Set.of();
        }
    }
    public static <T> List<T> toList(String json, TypeReference<List<T>> typeReference) {
        if (json == null || json.isEmpty()) {
            return List.of(); // Retorna una lista vacía si el JSON es nulo o vacío
        }

        try {
            return deserializerMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("Error al convertir el JSON a una lista de objetos: {}", e.getMessage());
            return List.of(); // Retorna una lista vacía si ocurre un errorTcp}
        }
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return List.of();
        }

        try {
            JavaType type = deserializerMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return deserializerMapper.readValue(json, type);
        } catch (Exception e) {
            log.warn("Error al convertir el JSON a una lista de objetos: {}", e.getMessage());
            return List.of();
        }
    }
    /**
     * Convierte cualquier objeto en una cadena JSON.
     *
     * @param object El objeto a convertir.
     * @return Una cadena JSON que representa el objeto.
     * @throws RuntimeException Si ocurre un errorTcp durante la conversión.
     */
    public static String toJson(Object object) {
        try {
            return serializerMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el objeto a JSON", e);
        }
    }

    /**
     * Convierte una cadena JSON en un objeto de tipo T.
     *
     * @param json La cadena JSON.
     * @param clazz El tipo de objeto al que se desea convertir.
     * @param <T> El tipo de objeto.
     * @return El objeto convertido.
     * @throws RuntimeException Si ocurre un errorTcp durante la conversión.
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return deserializerMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el JSON a objeto: " + e.getMessage() + "\nJSON: " + json, e);
        }
    }
    public static Map<String,Object> convertAttributesToMap(String attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return Map.of(); // Devuelve un mapa vacío si no hay atributos
        }

        try {
            return JsonUtils.toObject(attributes, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir 'attributes' en un mapa: " + e.getMessage());
        }
    }
    public static List<Map<String,Object>> convertAttributesToListMap(String attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return List.of(); // Devuelve un mapa vacío si no hay atributos
        }
        TypeReference<List<Map<String,Object>>> reference = new TypeReference<>() {};
        try {
            return JsonUtils.toList(attributes, reference);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir 'attributes' en un mapa: " + e.getMessage());
        }
    }
    /**
     * Convierte un objeto de tipo T en un objeto de tipo R.
     *
     * @param object El objeto de entrada.
     * @param targetClass La clase del tipo objetivo.
     * @param <T> El tipo del objeto de entrada.
     * @param <R> El tipo del objeto de salida.
     * @return El objeto convertido.
     * @throws RuntimeException Si ocurre un errorTcp durante la conversión.
     */
    public static <T, R> R convertObject(T object, Class<R> targetClass) {
        try {
            String json = toJson(object); // Convertir a JSON intermedio
            return toObject(json, targetClass); // Convertir al tipo objetivo
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el objeto entre tipos", e);
        }
    }
}
