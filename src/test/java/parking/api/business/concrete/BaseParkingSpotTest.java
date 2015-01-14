package parking.api.business.concrete;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parking.api.business.contract.Vehicle;
import parking.api.exceptions.BookingAlreadyConsumedException;
import parking.api.exceptions.BookingOverlapException;
import parking.api.exceptions.SpotNotEmptyOrBookedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BaseParkingSpotTest {
    BaseParkingSpot parkingSpot;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        parkingSpot = new BaseParkingSpot() {
            @Override
            public Integer getId() {
                return 1;
            }
        };
    }

    @Test
    public void testIsVehicleParked() throws SpotNotEmptyOrBookedException {
        assertFalse(parkingSpot.isVehicleParked());

        parkingSpot.park(mock(Vehicle.class));
        assertTrue(parkingSpot.isVehicleParked());

        parkingSpot.unpark();
        assertFalse(parkingSpot.isVehicleParked());
    }

    @Test
    public void testGetVehicle() throws SpotNotEmptyOrBookedException {
        assertNull(parkingSpot.getVehicle());
        Vehicle vehicle = mock(Vehicle.class);
        parkingSpot.park(vehicle);
        assertThat(parkingSpot.getVehicle(), is(vehicle));
    }

    @Test
    public void testPark() throws SpotNotEmptyOrBookedException {
        Vehicle vehicle = mock(Vehicle.class);
        parkingSpot.park(vehicle);

        assertThat(parkingSpot.getVehicle(), is(vehicle));
        assertThat(parkingSpot.unpark(), is(vehicle));

        thrown.expect(SpotNotEmptyOrBookedException.class);
        parkingSpot.park(mock(Vehicle.class));
        parkingSpot.park(mock(Vehicle.class));
    }

    @Test
    public void testUnpark() throws SpotNotEmptyOrBookedException {
        Vehicle vehicle = mock(Vehicle.class);

        parkingSpot.park(vehicle);
        assertThat(parkingSpot.getVehicle(), is(vehicle));
        assertThat(parkingSpot.unpark(), is(vehicle));
        assertNull(parkingSpot.getVehicle());
        assertNull(parkingSpot.unpark());
    }

    @Test
    public void testBooking() throws BookingOverlapException, BookingAlreadyConsumedException {
        assertFalse(parkingSpot.isBooked(Interval.parse("2001-01-03/P4D")));

        Booking booking = new Booking(Interval.parse("2001-01-01/P2W"));
        try {
            parkingSpot.book(, booking, );
        } catch (SpotNotEmptyOrBookedException e) {
            e.printStackTrace();
        }
        assertTrue(parkingSpot.isBooked(Interval.parse("2001-01-03/P4D")));
        assertFalse(parkingSpot.isBooked(Interval.parse("2014-12-31/P1YT22H30M48S")));

        parkingSpot.unbook(booking);
        assertFalse(parkingSpot.isBooked(Interval.parse("2001-01-01/P1D")));

        try {
            parkingSpot.book(, booking, );
        } catch (SpotNotEmptyOrBookedException e) {
            e.printStackTrace();
        }
        booking.setConsumed(true);
        assertTrue(booking.getConsumed());
        assertTrue(parkingSpot.isBooked(Interval.parse("2001-01-03/P4D")));

        thrown.expect(BookingAlreadyConsumedException.class);
        parkingSpot.unbook(booking);
    }

    @Test
    public void testFits() {
        // @todo Find if possible mock...
    }
}