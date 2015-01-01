package parking.api.business.concrete;

import org.joda.time.Interval;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class Booking {
    public Boolean consumed;
    private Interval interval;

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
}
