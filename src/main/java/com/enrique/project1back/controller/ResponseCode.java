package com.enrique.project1back.controller;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
    OK(0, "OK"),
    NOT_FOUND_ID(1, "Invalid ID: ID not found in database."),
    NOT_FOUND_EMAIL(2, "Invalid email: Email not found in database."),
    ALREADY_EXISTENT_USER(3, "User conflict: There is already a user with that email.");

    public final int code;
    public final String description;
    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
