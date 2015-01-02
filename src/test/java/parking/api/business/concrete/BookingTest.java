package parking.api.business.concrete;

import org.joda.time.Interval;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookingTest {
    @Test
    public void testOverlaps() {
        Booking a = new Booking(Interval.parse("2001-01-01/P2W"));
        Booking b = new Booking(Interval.parse("2001-01-14/P4D"));
        Booking c = new Booking(Interval.parse("2001-01-17T14:00/PT3H"));

        assertTrue(a.overlaps(b));
        assertTrue(b.overlaps(a.getInterval()));

        assertTrue(b.overlaps(c));
        assertTrue(c.overlaps(b));

        assertFalse(a.overlaps(c));
        assertFalse(c.overlaps(a));
    }
}