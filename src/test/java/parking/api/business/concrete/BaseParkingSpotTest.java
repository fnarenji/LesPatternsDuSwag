package parking.api.business.concrete;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parking.api.business.contract.Vehicle;
import parking.api.exceptions.SpotBookedException;
import parking.api.exceptions.SpotNotBookedException;
import parking.api.exceptions.SpotNotEmptyException;
import parking.api.exceptions.VehicleNotFitException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class BaseParkingSpotTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private BaseParkingSpot parkingSpot;

    @Before
    public void setUp() {
        parkingSpot = new BaseParkingSpot() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public Boolean fits(Vehicle v) {
                return true;
            }
        };
    }

    @Test
    public void testIsVehicleParked() throws SpotNotEmptyException, SpotBookedException, VehicleNotFitException {
        assertFalse(parkingSpot.isVehicleParked());

        parkingSpot.park(mock(Vehicle.class));
        assertTrue(parkingSpot.isVehicleParked());

        parkingSpot.unpark();
        assertFalse(parkingSpot.isVehicleParked());
    }

    @Test
    public void testGetVehicle() throws SpotNotEmptyException, SpotBookedException, VehicleNotFitException {
        assertNull(parkingSpot.getVehicle());
        Vehicle vehicle = mock(Vehicle.class);
        parkingSpot.park(vehicle);
        assertThat(parkingSpot.getVehicle(), is(vehicle));
    }

    @Test
    public void testPark() throws SpotNotEmptyException, SpotBookedException, VehicleNotFitException {
        Vehicle vehicle = mock(Vehicle.class);
        parkingSpot.park(vehicle);

        assertThat(parkingSpot.getVehicle(), is(vehicle));
        assertThat(parkingSpot.unpark(), is(vehicle));

        parkingSpot.park(mock(Vehicle.class));
        thrown.expect(SpotNotEmptyException.class);
        parkingSpot.park(mock(Vehicle.class));
    }

    @Test
    public void testUnpark() throws SpotNotEmptyException, SpotBookedException, VehicleNotFitException {
        Vehicle vehicle = mock(Vehicle.class);

        parkingSpot.park(vehicle);
        assertThat(parkingSpot.getVehicle(), is(vehicle));
        assertThat(parkingSpot.unpark(), is(vehicle));
        assertNull(parkingSpot.getVehicle());
        assertNull(parkingSpot.unpark());
    }

    @Test
    public void testBooking() throws SpotNotEmptyException, SpotNotBookedException, SpotBookedException {
        parkingSpot.book("CLIENT PLUTÔT STYLé", null);
        assertTrue(parkingSpot.isBooked());
        assertFalse(parkingSpot.isVehicleParked()); // A booked spot doesn't mean a vehicle is parked

        Booking booking = parkingSpot.unbook();
        assertFalse(parkingSpot.isBooked());

        assertEquals(booking.getOwner(), "CLIENT PLUTÔT STYLé");

        thrown.expect(SpotNotBookedException.class);
        parkingSpot.unbook();
    }

    @Test
    public void testBookingSpotNotEmpty() throws SpotNotEmptyException, SpotBookedException, SpotNotBookedException, VehicleNotFitException {
        parkingSpot.book("CLIENT VIVANT", null);
        assertTrue(parkingSpot.isBooked());
        assertFalse(parkingSpot.isVehicleParked());

        Vehicle vehicle = mock(Vehicle.class);
        when(vehicle.getOwner()).thenReturn("CLIENT VIVANT");

        parkingSpot.park(vehicle);
        assertTrue(parkingSpot.isVehicleParked());
        assertTrue(parkingSpot.isBooked());

        thrown.expect(SpotNotEmptyException.class);
        parkingSpot.unbook();
    }
    /*
    @Test
    public void testFits() {
        // @todo Find if possible mock...
    }
    */
}