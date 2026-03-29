package com.nilemobile.backend.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    CREATE_SUCCESS(201 ,"Create successfully"),
    UPDATE_SUCCESS(200, "Update successfully"),
    DELETE_SUCCESS(204, "Delete successfully"),

    GET_SUCCESS(200, "Get successfully");

    private final int code;
    private final String message;


}
