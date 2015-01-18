package parking.api.business.observer;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by sknz on 1/17/15.
 */
public class BaseObservable<T extends Observable> implements Observable<T> {
    private Collection<Observer<Observable<T>>> observers = new HashSet<>();

    @Override
    public void registerObserver(Observer<Observable<T>> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<Observable<T>> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.observe(this));
    }
}
