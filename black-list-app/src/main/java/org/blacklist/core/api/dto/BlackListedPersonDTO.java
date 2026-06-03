package org.blacklist.core.api.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlackListedPersonDTO {

    @Size(max = 200)
    private String personFirstName;

    @Size(max = 200)
    private String personLastName;

    @Size(max = 100)
    private String personCode;

    private Boolean blackListed;
}
