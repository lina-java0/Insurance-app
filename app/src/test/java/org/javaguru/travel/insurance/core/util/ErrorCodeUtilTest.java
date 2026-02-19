package org.javaguru.travel.insurance.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ErrorCodeUtilTest {

    @Mock
    private Properties props;

    @InjectMocks
    private ErrorCodeUtil errorCodeUtil;

    @Test
    @DisplayName("Should get error description")
    public void shouldGetErrorDescription() {
        when(props.getProperty("ERROR_CODE")).thenReturn("Error description");
        assertEquals("Error description", props.getProperty("ERROR_CODE"));
    }

    @Test
    @DisplayName("Should get error description using placeholder")
    public void shouldGetErrorDescriptionUsingPlaceholder() {
        Placeholder placeholder = new Placeholder("Placeholder", "124");
        when(props.getProperty("ERROR_CODE")).thenReturn("Error {Placeholder} description");
        assertEquals("Error 124 description", errorCodeUtil.getErrorDescription("ERROR_CODE", List.of(placeholder)));
    }
}
