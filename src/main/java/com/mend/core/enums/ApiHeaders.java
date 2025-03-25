package com.mend.core.enums;

public enum ApiHeaders {
    AUTHORIZATION("Authorization"),
    CONTENT_TYPE("Content-Type"),
    JSON_CONTENT_TYPE("application/json"),
    TOKEN_PREFIX("token ");

    private final String headerName;

    ApiHeaders(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}