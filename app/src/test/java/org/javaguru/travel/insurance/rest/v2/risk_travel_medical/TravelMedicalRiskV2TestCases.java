package org.javaguru.travel.insurance.rest.v2.risk_travel_medical;

import org.javaguru.travel.insurance.rest.v2.TravelCalculatePremiumControllerV2Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TravelMedicalRiskV2TestCases extends TravelCalculatePremiumControllerV2Test {

    private static final String TEST_FILE_BASE_FOLDER = "risk_travel_medical/";

    // ------------------------------------------------------------
    // GROUP 1: Success case (case 35)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Success case with TRAVEL_MEDICAL risk only")
    class successCaseWithTravelMedicalRisk {

        @Test
        @DisplayName("Success case with TRAVEL_MEDICAL risk only")
        void testCase35_successCaseWithTravelMedicalRisk() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_35", true);
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Medical Risk Limit Level value validation tests (cases 36-41)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Medical risk limit level validation")
    class MedicalRiskLimitLevelValidationTests {

        @Test
        @DisplayName("Test Case 36: one person medicalRiskLimitLevel value is null (TRAVEL_MEDICAL) → ERROR_CODE_14")
        void testCase36_onePersonMedicalRiskLimitLevelValueIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_36");
        }

        @Test
        @DisplayName("Test Case 37: one person medicalRiskLimitLevel value is empty (TRAVEL_MEDICAL) → ERROR_CODE_14")
        void testCase37_onePersonMedicalRiskLimitLevelValueIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_37");
        }

        @Test
        @DisplayName("Test Case 38: two people's medicalRiskLimitLevel value is null (TRAVEL_MEDICAL) → multiple ERROR_CODE_14")
        void testCase38_twoPeopleMedicalRiskLimitLevelValueIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_38");
        }

        @Test
        @DisplayName("Test Case 39: two people's medicalRiskLimitLevel value is empty (TRAVEL_MEDICAL) → multiple ERROR_CODE_14")
        void testCase39_twoPeopleMedicalRiskLimitLevelValueIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_39");
        }

        @Test
        @DisplayName("Test Case 40: one person medicalRiskLimitLevel value not supported (TRAVEL_MEDICAL) → ERROR_CODE_15")
        void testCase40_onePersonMedicalRiskLimitLevelValueUnsupported() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_40");
        }

        @Test
        @DisplayName("Test Case 41: two people's medicalRiskLimitLevel value not supported (TRAVEL_MEDICAL) → multiple ERROR_CODE_15")
        void testCase41_twoPeopleMedicalRiskLimitLevelValueUnsupported() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_41");
        }
    }
}
