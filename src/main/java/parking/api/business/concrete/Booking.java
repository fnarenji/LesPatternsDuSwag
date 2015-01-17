package parking.api.business.concrete;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.MutableInterval;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class Booking {
    public static final DateTime INFINITE = new DateTime().year().withMaximumValue();
    private Object owner;
    private Interval interval;

    public Booking(Object owner, DateTime until) {
        this.owner = owner;

        if (until == null)
            until = INFINITE; // should be quite safe...

        this.interval = new Interval(DateTime.now(), until);
    }

    public Interval getInterval() {
        return interval;
    }

    public void setUntil(DateTime until) {
        MutableInterval mutableInterval = interval.toMutableInterval();
        mutableInterval.setEnd(until.toInstant());
        interval = mutableInterval.toInterval();
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public boolean isOwnedBy(Object owner) {
        return this.owner.equals(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        return interval.equals(booking.interval);
    }

    @Override
    public int hashCode() {
        return interval.hashCode();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "interval=" + interval +
                '}';
    }
}
