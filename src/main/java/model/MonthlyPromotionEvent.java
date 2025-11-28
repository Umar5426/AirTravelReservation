package main.java.model;

import java.time.LocalDate;
import java.time.YearMonth;

public record MonthlyPromotionEvent(LocalDate date, YearMonth month) {
}
