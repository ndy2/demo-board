package com.example.demoboard.domain.util;

public class Result {
    private boolean valid;

    private String errorMessage;

    private Result(boolean valid) {
        this.valid = valid;
    }

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;

    }
    public boolean isValid() {
        return valid;
    }

    public static Result ok() {
        return new Result(true);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
