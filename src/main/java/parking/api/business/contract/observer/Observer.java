package parking.api.business.contract.observer;

/**
 * Created by sknz on 1/17/15.
 */
public interface Observer {
    /**
     * Notification callback.
     * @param observable the Observable emitting the notification
     */
    public void observe(Observable observable);
}
