package com.sapient.feecalc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {

    @JsonProperty("M")
    MALE("M", "Male"),
    @JsonProperty("F")
    FEMALE("F", "Female");

    private String code;
    private String value;

    private Gender(String code, String value) {
        this.code=code;
        this.value=value;
    }

    public String getCode(){
        return code;
    }

    public String getValue() {
        return value;
    }
}
