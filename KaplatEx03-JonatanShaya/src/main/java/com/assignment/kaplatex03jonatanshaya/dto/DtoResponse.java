package com.assignment.kaplatex03jonatanshaya.dto;


public class DtoResponse<T> {
    private final T result;
    private final String errorMessage;

    public DtoResponse(T result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public T getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
