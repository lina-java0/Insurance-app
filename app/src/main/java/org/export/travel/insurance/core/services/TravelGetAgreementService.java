package org.export.travel.insurance.core.services;

import org.export.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.export.travel.insurance.core.api.command.TravelGetAgreementCoreResult;

public interface TravelGetAgreementService {

    TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand command);

}
