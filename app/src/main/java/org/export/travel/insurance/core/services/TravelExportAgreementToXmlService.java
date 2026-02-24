package org.export.travel.insurance.core.services;

import org.export.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import org.export.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;

public interface TravelExportAgreementToXmlService {

    TravelExportAgreementToXmlCoreResult export(TravelExportAgreementToXmlCoreCommand command);
}
