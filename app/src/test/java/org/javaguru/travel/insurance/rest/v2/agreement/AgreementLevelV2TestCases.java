package org.javaguru.travel.insurance.rest.v2.agreement;

import org.javaguru.travel.insurance.rest.v2.TravelCalculatePremiumControllerV2Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Agreement level validation tests (V1)")
public class AgreementLevelV2TestCases extends TravelCalculatePremiumControllerV2Test {

    private static final String TEST_FILE_BASE_FOLDER = "agreement/";

    // ------------------------------------------------------------
    // GROUP 1: Agreement date form and date to validation tests (cases 23-27)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Agreement date validation")
    class AgreementDateValidationTests {

        @Test
        @DisplayName("Test Case 23: agreementDateFrom is null → ERROR_CODE_2")
        void testCase23_agreementDateFromIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_23");
        }

        @Test
        @DisplayName("Test Case 24: agreementDateTo is null → ERROR_CODE_4")
        void testCase24_agreementDateToIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_24");
        }

        @Test
        @DisplayName("Test Case 25: agreementDateFrom is in the past → ERROR_CODE_1")
        void testCase25_agreementDateFromInPast() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_25");
        }

        @Test
        @DisplayName("Test Case 26 agreementDateTo is in the past → ERROR_CODE_3")
        void testCase26agreementDateToInPast() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_26");
        }

        @Test
        @DisplayName("Test Case 27: agreementDateTo < agreementDateFrom → ERROR_CODE_5")
        void testCase27_agreementDateToBeforeDateFrom() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_27");
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Country validation tests (cases 28-32)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Country field validation")
    class CountryValidationTests {

        @Test
        @DisplayName("Test Case 28: country is null (TRAVEL_MEDICAL) → ERROR_CODE_10")
        void testCase28_countryIsNullMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_28");
        }

        @Test
        @DisplayName("Test Case 29: country is empty (TRAVEL_MEDICAL) → ERROR_CODE_10")
        void testCase29_countryIsEmptyMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_29");
        }

        @Test
        @DisplayName("Test Case 30: country is not supported (TRAVEL_MEDICAL) → ERROR_CODE_11")
        void testCase30_countryIsNotSupportedMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_30");
        }

        @Test
        @DisplayName("Test Case 31: country is null (TRAVEL_EVACUATION) → ERROR_CODE_10")
        void testCase31_countryIsNullEvacuation() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_31");
        }

        @Test
        @DisplayName("Test Case 32: country is empty (TRAVEL_EVACUATION) → ERROR_CODE_10")
        void testCase32_countryIsEmptyMedical() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_32");
        }
    }

    // ------------------------------------------------------------
    // GROUP 3: Fields not provided validation tests (cases 33-34)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Fields not provided validation")
    class FieldsNotProvidedValidationTests {

        @Test
        @DisplayName("Test Case 33: all fields not provided (TRAVEL_MEDICAL) → multiple error code")
        void testCase33_allFieldsNotProvidedTravelMedicalRisk() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_33");
        }

        @Test
        @DisplayName("Test Case 34: all fields not provided → multiple error code")
        void testCase34_allFieldsNotProvided() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_34");
        }
    }

    // ------------------------------------------------------------
    // GROUP 4: People list is empty validation test (cases 46)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("People list is empty validation")
    class PeopleListIsEmptyValidationTests {

        @Test
        @DisplayName("Test Case 46: people list is empty → multiple error code")
        void testCase46_peopleListIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_46");
        }
    }
}
