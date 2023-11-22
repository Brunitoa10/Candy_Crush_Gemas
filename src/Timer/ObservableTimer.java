package Timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// Custom event class to represent the tick
class TickEvent {
    // You can include additional information related to the event if needed
}

// Observable timer class
class ObservableTimer {
    private ScheduledExecutorService scheduler;
    private TickObserver tickObserver;
    private ScheduledFuture<?> future;

    public ObservableTimer() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        tickObserver = new TickObserver();

        // Schedule the task to execute every second
        future = scheduler.scheduleAtFixedRate(() -> notifyObservers(new TickEvent()), 1, 1, TimeUnit.SECONDS);
    }

    // Add an observer
    public void addObserver(TickObserver observer) {
        tickObserver = observer;
    }

    // Notify observers
    private void notifyObservers(TickEvent event) {
        tickObserver.update(event);
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
}

// Observer class
class TickObserver {
    public void update(TickEvent event) {
        // Do something when a second has passed
        System.out.println("Tick!");
    }
}


//ejemplo (gracias chatGPT)

/*
public class Main {
    public static void main(String[] args) {
        ObservableTimer observableTimer = new ObservableTimer();

        // Add the observer
        observableTimer.addObserver(new TickObserver());

        // Do other setup or start your application logic

        // For the sake of this example, let's pause and then resume the timer
        try {
            Thread.sleep(3000);  // Sleep for 3 seconds
            observableTimer.pause();

            Thread.sleep(3000);  // Sleep for another 3 seconds (timer is paused)
            observableTimer.resume();

            Thread.sleep(5000);  // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the timer (cleanup)
        observableTimer.stop();
    }
*/