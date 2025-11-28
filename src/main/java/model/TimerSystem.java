package main.java.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerSystem implements Subject<MonthlyPromotionEvent> {
    private final Set<Observer<MonthlyPromotionEvent>> observers = ConcurrentHashMap.newKeySet();
    private final Clock clock;
    private ScheduledExecutorService scheduler;
    private volatile YearMonth lastNotified;

    public TimerSystem() {
        this(Clock.systemDefaultZone());
    }

    public TimerSystem(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void register(Observer<MonthlyPromotionEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer<MonthlyPromotionEvent> observer) {
        observers.remove(observer);
    }

    public void start() {
        if (scheduler != null) return;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::tickNow, 0, 1, TimeUnit.HOURS);
    }

    public void stop() {
        if (scheduler == null) return;
        scheduler.shutdownNow();
        scheduler = null;
    }

    public void tickNow() {
        tick(LocalDate.now(clock));
    }

    public void tick(LocalDate today) {
        if (today.getDayOfMonth() != 1) return;

        YearMonth ym = YearMonth.from(today);
        if (ym.equals(lastNotified)) return;
        lastNotified = ym;

        MonthlyPromotionEvent event = new MonthlyPromotionEvent(today, ym);
        for (Observer<MonthlyPromotionEvent> o : observers) {
            o.update(event);
        }
    }
}
