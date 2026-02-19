package org.javaguru.travel.insurance.rest.v1.risk_travel_medical;

import org.javaguru.travel.insurance.rest.v1.TravelCalculatePremiumControllerV1Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TravelMedicalRiskV1TestCases extends TravelCalculatePremiumControllerV1Test {

    private static final String TEST_FILE_BASE_FOLDER = "risk_travel_medical/";

    // ------------------------------------------------------------
    // GROUP 1: Success case (case 24)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Success case with TRAVEL_MEDICAL risk only")
    class successCaseWithTravelMedicalRisk {

        @Test
        @DisplayName("Success case with TRAVEL_MEDICAL risk only")
        void testCase24_successCaseWithTravelMedicalRisk() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_24", true);
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Medical Risk Limit Level value validation tests (cases 25-27)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Medical risk limit level validation")
    class MedicalRiskLimitLevelValidationTests {

        @Test
        @DisplayName("Test Case 25: medicalRiskLimitLevel value is null (TRAVEL_MEDICAL) → ERROR_CODE_14")
        void testCase25_medicalRiskLimitLevelValueIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_25");
        }

        @Test
        @DisplayName("Test Case 26: medicalRiskLimitLevel value is empty (TRAVEL_MEDICAL) → ERROR_CODE_14")
        void testCase26_medicalRiskLimitLevelValueIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_26");
        }

        @Test
        @DisplayName("Test Case 27: medicalRiskLimitLevel value not supported (TRAVEL_MEDICAL) → ERROR_CODE_15")
        void testCase27_medicalRiskLimitLevelValueUnsupported() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_27");
        }
    }
}
