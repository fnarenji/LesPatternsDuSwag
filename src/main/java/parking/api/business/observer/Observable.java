package parking.api.business.observer;

/**
 * Created by sknz on 1/17/15.
 */
public interface Observable<T extends Observable> {
    /**
     * Register an observer. Everytime the object is modified, the observers will be notified.
     *
     * @param observer the observer to register
     */
    public void registerObserver(Observer<T> observer);

    /**
     * Unregister an observer. The observer won't be notified of changes.
     *
     * @param observer the observer to remove
     */
    public void unregisterObserver(Observer<T> observer);

    /**
     * Send a notification to all the observers.
     */
    public void notifyObservers();
}
