package main.java.model;

/*
 * Observer interface
 * Any class that wants to "listen" for the monthly promotion event
 * implements this interface.
 */
public interface Observer {
    void update(MonthlyPromotionEvent event);
}

