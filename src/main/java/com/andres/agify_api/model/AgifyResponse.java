// src/main/java/com/andres/agifyapi/model/AgifyResponse.java
package com.andres.agify_api.model;

public record AgifyResponse(
    String name,
    Integer age,
    Integer count
) {}