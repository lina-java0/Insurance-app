package org.export.travel.insurance.core.services;

import org.export.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.export.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;

public interface TravelGetNotExportedAgreementUuidsService {

    TravelGetNotExportedAgreementUuidsCoreResult getAgreementUuids(TravelGetNotExportedAgreementUuidsCoreCommand command);
}
