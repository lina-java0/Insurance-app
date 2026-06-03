package org.blacklist.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BlackListedPersonCheckTest extends BlackListedPersonCheckControllerTestCase {

    private static final String TEST_FILE_BASE_FOLDER_PERSON_ERRORS = "Person_errors/";
    private static final String TEST_FILE_BASE_FOLDER_PERSON_BLACKLISTED = "Person_blacklisted/";
    private static final String TEST_FILE_BASE_FOLDER_PERSON_NOT_BLACKLISTED = "Person_not_blacklisted/";

    // ------------------------------------------------------------
    // GROUP 1: Person validation tests (cases 1-6)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person errors validation")
    class PersonErrorsValidationTests {

        @Test
        @DisplayName("Test Case 1: personFirstName is empty → ERROR_CODE_1")
        void testCase1_personFirstNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_1");
        }

        @Test
        @DisplayName("Test Case 2: personFirstName is null → ERROR_CODE_1")
        void testCase2_personFirstNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_2");
        }

        @Test
        @DisplayName("Test Case 3: personLastName is empty → ERROR_CODE_2")
        void testCase3_personLastNameIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_3");
        }

        @Test
        @DisplayName("Test Case 4: personLastName is null → ERROR_CODE_2")
        void testCase4_personLastNameIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_4");
        }

        @Test
        @DisplayName("Test Case 5: personCode is empty → ERROR_CODE_3")
        void testCase5_testCase6_personCodeIsEmpty() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_5");
        }

        @Test
        @DisplayName("Test Case 6: personCode is null → ERROR_CODE_3")
        void testCase6_personCodeIsNull() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_ERRORS + "test_case_6");
        }
    }

    // ------------------------------------------------------------
    // GROUP 2: Person blacklisted tests (cases 7)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person blacklisted validation")
    class PersonBlacklistedValidationTests {

        @Test
        @DisplayName("Test Case 7: Person blacklisted")
        void testCase7_checkPersonBlacklisted() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_BLACKLISTED + "test_case_7");
        }
    }

    // ------------------------------------------------------------
    // GROUP 3: Person not blacklisted tests (cases 8)
    // ------------------------------------------------------------
    @Nested
    @DisplayName("Person not blacklisted validation")
    class PersonNotBlacklistedValidationTests {

        @Test
        @DisplayName("Test Case 8: Person not blacklisted")
        void testCase8_checkPersonNotBlacklisted() throws Exception {
            executeAndCompare(TEST_FILE_BASE_FOLDER_PERSON_NOT_BLACKLISTED + "test_case_8");
        }
    }
}
