package com.nilemobile.backend.reponse;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private int code;
    private String message;
    private Timestamp timestamp;
    private T body;
}
