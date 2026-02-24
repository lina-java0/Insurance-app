package org.export.travel.insurance.core.validations;

import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.util.ErrorCodeUtil;
import org.export.travel.insurance.core.util.Placeholder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationErrorFactoryTest {

    @Mock private ErrorCodeUtil errorCodeUtil;

    @InjectMocks
    private ValidationErrorFactory errorFactory;

    @Test
    @DisplayName("Should return validation error with description")
    public void shouldReturnValidationErrorWithDescription() {
        when(errorCodeUtil.getErrorDescription("ERROR_CODE"))
                .thenReturn("Error description");
        ValidationErrorDTO error = errorFactory.buildError("ERROR_CODE");
        assertEquals("ERROR_CODE", error.getErrorCode());
        assertEquals( "Error description", error.getDescription());
    }

    @Test
    @DisplayName("Should return validation error with description using placeholder")
    public void shouldReturnValidationErrorWithDescriptionUsingPlaceholder() {
        Placeholder placeholder = new Placeholder("Placeholder", "124");
        when(errorCodeUtil.getErrorDescription("ERROR_CODE", List.of(placeholder)))
                .thenReturn("Error 124 description");
        ValidationErrorDTO error = errorFactory.buildError("ERROR_CODE", List.of(placeholder));
        assertEquals("ERROR_CODE", error.getErrorCode());
        assertEquals("Error 124 description", error.getDescription());
    }
}