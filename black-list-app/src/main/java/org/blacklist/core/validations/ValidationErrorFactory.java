package org.blacklist.core.validations;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.blacklist.core.api.dto.ValidationErrorDTO;
import org.blacklist.core.util.ErrorCodeUtil;
import org.blacklist.core.util.Placeholder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    public ValidationErrorDTO buildError(String errorCode) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }

    public ValidationErrorDTO buildError(String errorCode, List<Placeholder> placeholders) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }
}
