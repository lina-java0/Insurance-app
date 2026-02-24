package org.export.travel.insurance.core.underwriting;

import org.export.travel.insurance.core.api.dto.RiskDTO;

import java.math.BigDecimal;
import java.util.List;

public record TravelPremiumCalculationResult(
    BigDecimal totalPremium,
    List<RiskDTO> risks
) {}
