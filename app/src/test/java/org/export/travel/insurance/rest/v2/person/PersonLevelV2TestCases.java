package org.export.travel.insurance.rest.v2.person;

import org.export.travel.insurance.rest.v2.TravelCalculatePremiumControllerV2Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PersonLevelV2TestCases extends TravelCalculatePremiumControllerV2Test {

    private static final String TEST_FILE_BASE_FOLDER = "person/";

    // ------------------------------------------------------------
    // GROUP 1: Person first and last names validation (cases 1-12)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person first and last names validation")
    class PersonNameValidationTests {

        @Test
        @DisplayName("Test Case 1: one person personFirstName is null → ERROR_CODE_7")
        void testCase1_onePersonFirstNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_1");
        }

        @Test
        @DisplayName("Test Case 2: one person personFirstName is empty → ERROR_CODE_7")
        void testCase2_onePersonFirstNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_2");
        }

        @Test
        @DisplayName("Test Case 3: two people's personFirstName is null → multiple ERROR_CODE_7")
        void testCase3_twoPeoplePersonFirstNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_3");
        }

        @Test
        @DisplayName("Test Case 4: two people's personFirstName is empty → multiple ERROR_CODE_7")
        void testCase4_twoPeoplePersonFirstNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_4");
        }

        @Test
        @DisplayName("Test Case 5: one person personFirstName format is not valid → ERROR_CODE_21")
        void testCase5_onePersonFirstNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_5");
        }

        @Test
        @DisplayName("Test Case 6: two people's personFirstName format is not valid → multiple ERROR_CODE_21")
        void testCase6_twoPeoplePersonFirstNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_6");
        }

        @Test
        @DisplayName("Test Case 7: one person personLastName is null → ERROR_CODE_8")
        void testCase7_onePersonLastNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_7");
        }

        @Test
        @DisplayName("Test Case 8: one person personLastName is empty → ERROR_CODE_8")
        void testCase8_onePersonLastNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_8");
        }

        @Test
        @DisplayName("Test Case 9: two people's personLastName is null → multiple ERROR_CODE_8")
        void testCase9_twoPeoplePersonLastNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_9");
        }

        @Test
        @DisplayName("Test Case 10: two people's personLastName is empty → multiple ERROR_CODE_8")
        void testCase10_twoPeoplePersonLastNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_10");
        }

        @Test
        @DisplayName("Test Case 11: one person personLastName format is not valid → ERROR_CODE_22")
        void testCase11_onePersonLastNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_11");
        }

        @Test
        @DisplayName("Test Case 12: two people's personLastName format is not valid → multiple ERROR_CODE_22")
        void testCase12_twoPeoplePersonLastNameFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_12");
        }

        @Test
        @DisplayName("Test Case 46: one person personFirstName is too long → ERROR_CODE_23")
        void testCase46_onePersonFirstNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_46");
        }

        @Test
        @DisplayName("Test Case 47: two people's personFirstName is too long → multiple ERROR_CODE_23")
        void testCase47_twoPeoplePersonFirstNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_47");
        }

        @Test
        @DisplayName("Test Case 48: one person personLastName is too long → ERROR_CODE_23")
        void testCase48_onePersonLastNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_48");
        }

        @Test
        @DisplayName("Test Case 49: two people's personLastName is too long → multiple ERROR_CODE_23")
        void testCase49_twoPeoplePersonLastNameIsTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_49");
        }

        @Test
        @DisplayName("Test Case 50: one person personFirstName and personLastName are too long → multiple ERROR_CODE_23")
        void testCase50_onePersonFirstAndLastNameAreTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_50");
        }

        @Test
        @DisplayName("Test Case 51: two people's personFirstName and personLastName are too long → multiple ERROR_CODE_23")
        void testCase51_twoPeoplePersonFirstAndLastNameAreTooLong() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_51");
        }
    }
    // ------------------------------------------------------------
    // GROUP 2: Person code validation (cases 13-18)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person code validation")
    class PersonCodeValidationTests {

        @Test
        @DisplayName("Test Case 13: one person personCode is null → ERROR_CODE_16")
        void testCase13_onePersonCodeIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_13");
        }

        @Test
        @DisplayName("Test Case 14: one person personCode is empty → ERROR_CODE_16")
        void testCase14_onePersonCodeIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_14");
        }

        @Test
        @DisplayName("Test Case 15: two people's personCode is null → multiple ERROR_CODE_16")
        void testCase15_twoPeoplePersonCodeIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_15");
        }

        @Test
        @DisplayName("Test Case 16: two people's personCode is empty → multiple ERROR_CODE_16")
        void testCase16_twoPeoplePersonCodeIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_16");
        }

        @Test
        @DisplayName("Test Case 17: one person personCode format is not valid → ERROR_CODE_20")
        void testCase17_onePersonCodeFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_17");
        }

        @Test
        @DisplayName("Test Case 18: two people's personCode format is not valid → multiple ERROR_CODE_20")
        void testCase18_twoPeoplePersonCodeFormatIsNotValid() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_18");
        }
    }

    // ------------------------------------------------------------
    // GROUP 3: Person birthdate validation (cases 19-22)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person birthdate validation")
    class PersonBirthDateValidationTests {

        @Test
        @DisplayName("Test Case 19: one person personBirthDate is null → ERROR_CODE_12")
        void testCase19_onePersonBirthDateIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_19");
        }

        @Test
        @DisplayName("Test Case 20: two people's personBirthDate is null → multiple ERROR_CODE_12")
        void testCase20_twoPeoplePersonBirthDateIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_20");
        }

        @Test
        @DisplayName("Test Case 21: one person personBirthDate is in the future → ERROR_CODE_13")
        void testCase21_onePersonBirthDateInFuture() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_21");
        }

        @Test
        @DisplayName("Test Case 22: two people's personBirthDate is in the future → multiple ERROR_CODE_13")
        void testCase22_twoPeoplePersonBirthDateInFuture() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER + "test_case_22");
        }
    }
}
