package org.export.travel.insurance.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeUtilTest {

    DateTimeUtil dateTimeUtil = new DateTimeUtil();

    private record TestCase(String date1, String date2, Long expectedDays, boolean expectException) {}

    static Stream<TestCase> provideDates() {
        return Stream.of(
                new TestCase("24.11.2026", "24.11.2026", 0L, false),
                new TestCase("24.11.2026", "29.11.2026", 5L, false),
                new TestCase("29.11.2026", "24.11.2026", -5L, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDates")
    @DisplayName("Should calculate days between correctly")
    void shouldCalculateDaysBetweenCorrectly(TestCase testCase) {
        LocalDate date1 = createDate(testCase.date1());
        LocalDate date2 = createDate(testCase.date2());

        if (testCase.expectException()) {
            assertThrows(IllegalArgumentException.class, () -> dateTimeUtil.calculateDaysBetween(date1, date2));
        } else {
            long actual = dateTimeUtil.calculateDaysBetween(date1, date2);
            assertEquals(testCase.expectedDays(), actual);
        }
    }

    private LocalDate createDate(String date) {
        if (date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

}
