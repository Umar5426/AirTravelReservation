package main.java.service;

import main.java.model.MonthlyPromotionEvent;
import main.java.model.Observer;

import javax.swing.SwingUtilities;

/*
 * This is an abstract service that listens for MonthlyPromotionEvent.
 * It is the Observer in the Observer pattern.
 *
 * The idea:
 * - TimerSystem triggers update(event)
 * - This service then calls onMonthlyPromotion(event)
 * - Subclasses implement what to do:
 *      - CRUD/DB writes via DAO
 *      - show GUI popup / badge / message
 */
public abstract class NotificationsService implements Observer {

    @Override
    public final void update(MonthlyPromotionEvent event) {
        onMonthlyPromotion(event);
    }

    /*
     * Subclasses implement the actual behavior:
     * - store notifications in DB (Create/Read/etc.)
     * - notify GUI
     */
    protected abstract void onMonthlyPromotion(MonthlyPromotionEvent event);

    /*
     * Helper to safely update Swing GUI components.
     * If your GUI uses Swing, UI updates should happen on the Event Dispatch Thread (EDT).
     */
    protected final void runOnUIThread(Runnable task) {
        if (task == null) return;

        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }
}
