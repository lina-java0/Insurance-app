package org.export.travel.insurance.rest.v1.selected_risks;

import org.export.travel.insurance.rest.v1.TravelCalculatePremiumControllerV1Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RiskLevelV1TestCases extends TravelCalculatePremiumControllerV1Test {

    private static final String TEST_FILE_BASE_FOLDER = "selected_risks/";

    // ------------------------------------------------------------
    // GROUP 1: Selected risks validation tests (cases 28-31)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Selected risks validation")
    class SelectedRisksValidationTests {

        @Test
        @DisplayName("Test Case 28: selectedRisks value is null → ERROR_CODE_6")
        void testCase28_selectedRisksValueIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_28");
        }

        @Test
        @DisplayName("Test Case 29: selectedRisks value is empty → ERROR_CODE_6")
        void testCase29_selectedRisksValueIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_29");
        }

        @Test
        @DisplayName("Test Case 30: selectedRisks value contains unsupported risk (one risk) → ERROR_CODE_9")
        void testCase30_selectedRisksValueUnsupportedOne() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_30");
        }

        @Test
        @DisplayName("Test Case 31: selectedRisks value contains multiple unsupported risks → multiple ERROR_CODE_9")
        void testCase31_selectedRisksValueUnsupportedMultiple() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_31");
        }
    }
}
