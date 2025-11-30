package main.java.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Vector;

/*
 * TimerSystem represents the external Timer/Clock actor in the use-case diagram.
 * It is the Subject in the Observer pattern:
 *  - Observers register() to get notified
 *  - When the 1st day of the month happens, TimerSystem notifies observers
 *
 * IMPORTANT:
 * TimerSystem does NOT do any GUI code or DB code.
 * It only triggers notifications by calling update(...) on observers.
 */
public class TimerSystem implements Subject {


    private final Vector observers;

    /*
     * Prevent sending multiple notifications in the same month.
     * Even if GUI calls checkAndNotify(...) many times, we only notify once per month.
     */
    private YearMonth lastNotifiedMonth;

    public TimerSystem() {
        observers = new Vector();
        lastNotifiedMonth = null;
    }

    @Override
    public void register(Observer observer) {
        if (observer == null) return;
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    /*
     * The GUI (or app startup) can call this daily, hourly, or on startup.
     * If today is day 1, we trigger the monthly promotion event.
     */
    public void checkAndNotify(LocalDate today) {
        if (today == null) return;

        if (today.getDayOfMonth() != 1) {
            return;
        }

        YearMonth currentMonth = YearMonth.from(today);

        if (lastNotifiedMonth != null && lastNotifiedMonth.equals(currentMonth)) {
            return;
        }

        lastNotifiedMonth = currentMonth;

        MonthlyPromotionEvent event = new MonthlyPromotionEvent(today, currentMonth);
        notifyObservers(event);
    }

    /*
     * Notifies every registered observer (TimerSystem does not decide what happens next).
     * Observers can:
     *  - write to DB (CRUD)
     *  - update GUI
     *  - create notifications, etc.
     */
    private void notifyObservers(MonthlyPromotionEvent event) {
        for (int i = 0; i < observers.size(); i++) {
            Observer obs = (Observer) observers.get(i);
            obs.update(event);
        }
    }
}

