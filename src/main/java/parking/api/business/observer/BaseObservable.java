package parking.api.business.observer;

import parking.api.business.Utils;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by sknz on 1/17/15.
 */
public class BaseObservable<T extends Observable> implements Observable<T> {
    private Collection<Observer<T>> observers = new HashSet<>();

    @Override
    public void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.observe((T)(this)));
    }
}
