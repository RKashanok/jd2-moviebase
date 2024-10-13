package com.jd2.moviebase;

import java.time.LocalDate;

import static java.time.Instant.now;
import static java.time.ZoneOffset.UTC;

public class Runner {
    public static void main(String[] args) {
        LocalDate deliveryDeadline = LocalDate.of(2024, 9, 10);
        boolean afterOrEqual = isAfterOrEqual(deliveryDeadline, now().atZone(UTC).toLocalDate());
        System.out.println("after_or_equal: " + afterOrEqual);
    }

    public static boolean isAfterOrEqual(LocalDate date, LocalDate input) {
        return date != null && (date.isAfter(input) || date.isEqual(input));
    }
}
