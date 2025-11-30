package main.java.model;

import java.time.LocalDate;
import java.time.YearMonth;

/*
 * Simple event object that carries information about the monthly promotion trigger.
 * This is what gets passed to observers.
 */
public class MonthlyPromotionEvent {
    private final LocalDate date;
    private final YearMonth month;

    public MonthlyPromotionEvent(LocalDate date, YearMonth month) {
        this.date = date;
        this.month = month;
    }

    public LocalDate getDate() {
        return date;
    }

    public YearMonth getMonth() {
        return month;
    }
}
