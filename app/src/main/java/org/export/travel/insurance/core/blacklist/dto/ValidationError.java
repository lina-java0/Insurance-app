package org.export.travel.insurance.core.blacklist.dto;

public record ValidationError (
    String errorCode,
    String description
) {}
