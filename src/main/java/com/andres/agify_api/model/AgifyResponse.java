// src/main/java/com/andres/agifyapi/model/AgifyResponse.java
package com.andres.agify_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgifyResponse(
    String name,
    Integer age,
    Integer count,
    String country_id
) {}