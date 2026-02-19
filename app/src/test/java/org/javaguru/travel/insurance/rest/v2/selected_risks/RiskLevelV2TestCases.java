package org.javaguru.travel.insurance.rest.v2.selected_risks;

import org.javaguru.travel.insurance.rest.v2.TravelCalculatePremiumControllerV2Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RiskLevelV2TestCases extends TravelCalculatePremiumControllerV2Test {

    private static final String TEST_FILE_BASE_FOLDER = "selected_risks/";

    // ------------------------------------------------------------
    // GROUP 1: Selected risks validation tests (cases 42-45)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Selected risks validation")
    class SelectedRisksValidationTests {

        @Test
        @DisplayName("Test Case 42: selectedRisks value is null → ERROR_CODE_6")
        void testCase42_selectedRisksValueIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_42");
        }

        @Test
        @DisplayName("Test Case 43: selectedRisks value is empty → ERROR_CODE_6")
        void testCase43_selectedRisksValueIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_43");
        }

        @Test
        @DisplayName("Test Case 44: selectedRisks value contains unsupported risk (one risk) → ERROR_CODE_9")
        void testCase44_selectedRisksValueUnsupportedOne() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_44");
        }

        @Test
        @DisplayName("Test Case 45: selectedRisks value contains multiple unsupported risks → multiple ERROR_CODE_9")
        void testCase45_selectedRisksValueUnsupportedMultiple() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_45");
        }
    }
}
