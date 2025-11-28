package main.java.service;

import main.java.model.MonthlyPromotionEvent;
import main.java.model.Observer;

public abstract class NotificationsService implements Observer<MonthlyPromotionEvent> {
    @Override
    public final void update(MonthlyPromotionEvent event) {
        sendMonthlyPromotion(event);
    }

    protected abstract void sendMonthlyPromotion(MonthlyPromotionEvent event);
}
