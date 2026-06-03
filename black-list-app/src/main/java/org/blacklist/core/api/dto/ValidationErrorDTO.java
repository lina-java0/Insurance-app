package org.blacklist.core.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDTO {

    private String errorCode;

    private String description;

}
