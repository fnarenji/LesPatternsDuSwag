package parking.api.business.concrete;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.MutableInterval;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class Booking {
    private static final DateTime INFINITE = new DateTime().year().withMaximumValue();
    private Object owner;
    private Interval interval;

    /**
     * Create a reservation
     * @param owner owner of the booking
     * @param until end date of the booking
     */
    public Booking(Object owner, DateTime until) {
        this.owner = owner;

        if (until == null)
            until = INFINITE; // should be quite safe...

        this.interval = new Interval(DateTime.now(), until);
    }

    /**
     * Get the interval of the booking
     * @return interval which the duration of the booking
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     *Assign the date of the end of the booking to the interval
     * @param until date of the end of the boking
     */
    public void setUntil(DateTime until) {
        MutableInterval mutableInterval = interval.toMutableInterval();

        if (until == null)
            until = INFINITE;

        mutableInterval.setEnd(until.toInstant());

        interval = mutableInterval.toInterval();
    }

    /**
     * Get the owner of the booking
     * @return the owner of the booking
     */
    public Object getOwner() {
        return owner;
    }

    /**
     * Set the owner of the booking
     * @param owner the person which own the booking
     */
    public void setOwner(Object owner) {
        this.owner = owner;
    }

    /**
     * Check the owner of the booking
     * @param owner the owner we want to test
     * @return true if the booking is owned by the owner passed as parameter
     */
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
