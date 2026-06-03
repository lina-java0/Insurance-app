package org.blacklist.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedPersonCheckRequest {

    private String personFirstName;

    private String personLastName;

    private String personCode;
}
