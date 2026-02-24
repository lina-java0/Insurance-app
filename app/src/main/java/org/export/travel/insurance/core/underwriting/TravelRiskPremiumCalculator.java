package org.export.travel.insurance.core.underwriting;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;

import java.math.BigDecimal;

public interface TravelRiskPremiumCalculator {
    BigDecimal calculatePremium(AgreementDTO agreement, PersonDTO person);
    String getRiskIc();
}
