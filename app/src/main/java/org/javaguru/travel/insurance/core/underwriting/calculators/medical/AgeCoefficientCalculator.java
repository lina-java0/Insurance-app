package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgeCoefficientCalculator {

    @Value( "${medical.risk.age.coefficient.enabled:false}" )
    private Boolean medicalRiskAgeCoefficientEnabled;

    private final DateTimeUtil dateTimeUtil;
    private final AgeCoefficientRepository ageCoefficientRepository;

    BigDecimal calculate(PersonDTO personDTO) {
        return medicalRiskAgeCoefficientEnabled
                ? getCoefficient(personDTO)
                : getDefaultValue();
    }

    private BigDecimal getCoefficient(PersonDTO personDTO) {
        int age = calculateAge(personDTO);
        return ageCoefficientRepository.findCoefficient(age)
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found for age = " + age));
    }

    private Integer calculateAge(PersonDTO personDTO) {
        LocalDate birthDay = personDTO.getPersonBirthDate();
        LocalDate currentDay = dateTimeUtil.getCurrentDate();
        return Period.between(birthDay, currentDay).getYears();
    }

    private static BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }
}
