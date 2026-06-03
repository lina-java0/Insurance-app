package org.blacklist.dto;

public record ValidationError (
    String errorCode,
    String description
) {}
