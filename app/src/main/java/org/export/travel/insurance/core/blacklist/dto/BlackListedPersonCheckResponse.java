package org.export.travel.insurance.core.blacklist.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackListedPersonCheckResponse extends CoreResponse {

    private String personFirstName;

    private String personLastName;

    private String personCode;

    private Boolean blacklisted;

    public BlackListedPersonCheckResponse(List<ValidationError> errors) {
        super(errors);
    }
}

