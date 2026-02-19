package org.javaguru.travel.insurance.rest.v1.agreement;

import org.javaguru.travel.insurance.rest.v1.TravelCalculatePremiumControllerV1Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@DisplayName("Agreement level validation tests (V1)")
public class AgreementLevelV1TestCases extends TravelCalculatePremiumControllerV1Test {

    private static final String TEST_FILE_BASE_FOLDER = "agreement/";

    // ------------------------------------------------------------
    // GROUP 1: Agreement date form and date to validation tests (cases 12-16)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Agreement date validation")
    class AgreementDateValidationTests {

        @Test
        @DisplayName("Test Case 12: agreementDateFrom is null → ERROR_CODE_2")
        void testCase12_agreementDateFromIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_12");
        }

        @Test
        @DisplayName("Test Case 13: agreementDateTo is null → ERROR_CODE_4")
        void testCase13_agreementDateToIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_13");
        }

        @Test
        @DisplayName("Test Case 14: agreementDateFrom is in the past → ERROR_CODE_1")
        void testCase14_agreementDateFromInPast() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_14");
        }

        @Test
        @DisplayName("Test Case 15: agreementDateTo is in the past → ERROR_CODE_3")
        void testCase15_agreementDateToInPast() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_15");
        }

        @Test
        @DisplayName("Test Case 16: agreementDateTo < agreementDateFrom → ERROR_CODE_5")
        void testCase16_agreementDateToBeforeDateFrom() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_16");
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Country validation tests (cases 17–21)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Country field validation")
    class CountryValidationTests {

        @Test
        @DisplayName("Test Case 17: country is null (TRAVEL_MEDICAL) → ERROR_CODE_10")
        void testCase17_countryIsNullMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_17");
        }

        @Test
        @DisplayName("Test Case 18: country is empty (TRAVEL_MEDICAL) → ERROR_CODE_10")
        void testCase18_countryIsEmptyMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_18");
        }

        @Test
        @DisplayName("Test Case 19: country is not supported (TRAVEL_MEDICAL) → ERROR_CODE_11")
        void testCase19_countryIsNotSupportedMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_19");
        }

        @Test
        @DisplayName("Test Case 20: country is null (TRAVEL_EVACUATION) → ERROR_CODE_10")
        void testCase20_countryIsNullEvacuation() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_20");
        }

        @Test
        @DisplayName("Test Case 21: country is empty (TRAVEL_EVACUATION) → ERROR_CODE_10")
        void testCase21_countryIsEmptyMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_21");
        }
    }

    // ------------------------------------------------------------
    // GROUP 3: Fields not provided validation tests (cases 22-23)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Fields not provided validation")
    class FieldsNotProvidedValidationTests {

        @Test
        @DisplayName("Test Case 22: all fields not provided (TRAVEL_MEDICAL) → multiple error code")
        void testCase22_allFieldsNotProvidedMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_22");
        }

        @Test
        @DisplayName("Test Case 23: all fields not provided → multiple error code")
        void testCase23_allFieldsNotProvided() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_23");
        }
    }
}
