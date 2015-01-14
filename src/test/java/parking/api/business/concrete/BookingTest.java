package parking.api.business.concrete;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BookingTest {
    @Test
    public void testInterval() {
        Booking a = new Booking("TEST", DateTime.now().plusDays(30));
        assertTrue(Seconds.secondsBetween(a.getInterval().getEnd(), DateTime.now().plusDays(30)).isLessThan(Seconds.seconds(5)));
        a.setUntil(DateTime.now().plusDays(60));
        assertTrue(Seconds.secondsBetween(a.getInterval().getEnd(), DateTime.now().plusDays(60)).isLessThan(Seconds.seconds(5)));
        assertTrue(a.isOwnedBy("TEST"));
    }
}