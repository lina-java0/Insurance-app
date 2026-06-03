package org.blacklist.core.services;

import org.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.blacklist.core.api.command.BlackListedPersonCoreResult;

public interface BlackListedPersonService {

    BlackListedPersonCoreResult checkPerson(BlackListedPersonCoreCommand command);
}
