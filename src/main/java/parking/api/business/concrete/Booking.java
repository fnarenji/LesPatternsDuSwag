package parking.api.business.concrete;

import org.joda.time.Interval;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class Booking {
    public Boolean consumed = false;
    private Interval interval;

    public Booking(Interval interval) {
        this.interval = interval;
    }

    public Booking(Interval interval, Boolean consumed) {
        this.interval = interval;
        this.consumed = consumed;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Boolean getConsumed() {
        return consumed;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }

    public Boolean overlaps(Interval interval) {
        return this.interval.overlaps(interval);
    }
    public Boolean overlaps(Booking booking) {
        return this.interval.overlaps(booking.getInterval());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (!consumed.equals(booking.consumed)) return false;
        if (!interval.equals(booking.interval)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = consumed.hashCode();
        result = 31 * result + interval.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "consumed=" + consumed +
                ", interval=" + interval +
                '}';
    }
}
