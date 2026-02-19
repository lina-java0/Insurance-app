package org.javaguru.travel.insurance.core.api.dto;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    @XmlElement
    @Size(max = 200)
    private String personFirstName;

    @XmlElement
    @Size(max = 200)
    private String personLastName;

    @XmlElement
    private String personCode;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate personBirthDate;

    @XmlElement
    private String medicalRiskLimitLevel;

    @XmlElementWrapper(name = "risks")
    @XmlElement(name = "risk")
    private List<RiskDTO> risks;
}
