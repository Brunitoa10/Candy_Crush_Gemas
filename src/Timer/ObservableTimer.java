package Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// Custom event class to represent the tick


// Observable timer class
public class ObservableTimer {
    private ScheduledExecutorService scheduler;
    private List<TickObserver> observers;
    private ScheduledFuture<?> future;

    public ObservableTimer() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        observers = new ArrayList<>();

        // Schedule the task to execute every second
        future = scheduler.scheduleAtFixedRate(() -> notifyObservers(new TickEvent()), 1, 1, TimeUnit.SECONDS);
    }

    // Add an observer
    public void addObserver(TickObserver observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void desuscribirse(TickObserver observer) {
        observers.remove(observer);
    }

    // Notify observers
    private void notifyObservers(TickEvent event) {
        for (TickObserver observer : observers) {
            observer.update(event);
        }
    }


    // Pause the timer
    public void pause() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }


    // Resume the timer
    public void resume() {
        if (future == null || future.isCancelled()) {
            future = scheduler.scheduleAtFixedRate(() -> notifyObservers(new TickEvent()), 1, 1, TimeUnit.SECONDS);
        }
    }

    // Stop the timer and cleanup
    public void stop() {
        scheduler.shutdown();
    }

    public void desuscribirTodo() {
        for(int i = 0; i<observers.size(); i++) {
            desuscribirse(observers.get(i));
        }
    }
}