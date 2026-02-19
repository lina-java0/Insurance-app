package org.javaguru.travel.insurance.rest.v1.person;

import org.javaguru.travel.insurance.rest.v1.TravelCalculatePremiumControllerV1Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PersonLevelV1TestCases extends TravelCalculatePremiumControllerV1Test {

    private static final String TEST_FILE_BASE_FOLDER = "person/";

    // ------------------------------------------------------------
    // GROUP 1: Person first and last names validation tests (cases 1-6)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person first and last names validation")
    class PersonNameValidationTests {

        @Test
        @DisplayName("Test Case 1: personFirstName is null → ERROR_CODE_7")
        void testCase1_personFirstNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_1");
        }

        @Test
        @DisplayName("Test Case 2: personFirstName is empty → ERROR_CODE_7")
        void testCase2_personFirstNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_2");
        }

        @Test
        @DisplayName("Test Case 3: personFirstName format is not valid → ERROR_CODE_21")
        void testCase3_personFirstNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_3");
        }

        @Test
        @DisplayName("Test Case 4: personLastName is null → ERROR_CODE_8")
        void testCase4_personLastNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_4");
        }

        @Test
        @DisplayName("Test Case 5: personLastName is empty → ERROR_CODE_8")
        void testCase5_personLastNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_5");
        }


        @Test
        @DisplayName("Test Case 6:personLastName format is not valid → ERROR_CODE_22")
        void testCase6_personLastNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_6");
        }

        @Test
        @DisplayName("Test Case 32: personFirstName is too long → ERROR_CODE_23")
        void testCase32_personFirstNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_32");
        }

        @Test
        @DisplayName("Test Case 33: personLastName is too long → ERROR_CODE_23")
        void testCase33_personLastNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_33");
        }

        @Test
        @DisplayName("Test Case 34: personFirstName and personLastName are too long → ERROR_CODE_23")
        void testCase34_personFirstAndLastNameAreTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_34");
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Person code validation tests (cases 7-9)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person code validation")
    class PersonCodeValidationTests {

        @Test
        @DisplayName("Test Case 7: personCode is null → ERROR_CODE_16")
        void testCase7_personCodeIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_7");
        }

        @Test
        @DisplayName("Test Case 8: personCode is empty → ERROR_CODE_16")
        void testCase8_personCodeIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_8");
        }

        @Test
        @DisplayName("Test Case 9: personCode format is not valid → ERROR_CODE_20")
        void testCase9_personCodeFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_9");
        }
    }

    // ------------------------------------------------------------
    // GROUP 3: Person birthdate validation tests (cases 10-11)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person birthdate validation")
    class PersonBirthDateValidationTests {

        @Test
        @DisplayName("Test Case 10: personBirthDate is null → ERROR_CODE_12")
        void testCase10_personBirthDateIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_10");
        }

        @Test
        @DisplayName("Test Case 11: personBirthDate is in the future → ERROR_CODE_13")
        void testCase11_personBirthDateInFuture() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_11");
        }
    }
}
