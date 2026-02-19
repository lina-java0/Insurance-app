package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DayCountCalculator {

    private final DateTimeUtil dateTimeUtil;

    BigDecimal calculate(AgreementDTO agreementDTO) {
        var daysBetween = dateTimeUtil.calculateDaysBetween(agreementDTO.getAgreementDateFrom(), agreementDTO.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }
}
