package parking.api.business.observer;

/**
 * Created by sknz on 1/17/15.
 */
public interface Observer<T extends Observable> {
    /**
     * Notification callback.
     *
     * @param observable the Observable emitting the notification
     */
    public void observe(T observable);
}
