package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private String personFirstName;

    private String personLastName;

    private String personCode;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;
}
