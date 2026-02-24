package org.export.travel.insurance.core.services;


import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;

public interface TravelCalculatePremiumService {

    TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command);

}
