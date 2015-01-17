package parking.api.business.concrete;

import org.junit.Before;
import org.junit.Test;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotFactory;
import parking.api.business.contract.ParkingSpotIdProvider;
import parking.api.business.contract.Vehicle;
import parking.api.exceptions.ParkingBookedSpotsExceptions;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.CarParkingSpot;
import parking.implementation.SerializeParkingManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingTest {
    private Parking parking;

    @Before
    public void setUp() throws ParkingExistsException, ParkingBookedSpotsExceptions, ParkingNotPresentException {
        ParkingManager parkingManager = ParkingManager.getInstance();
        if (parkingManager.containsParking(1))
            parkingManager.deleteParking(1);

        parking = parkingManager.newParking(1, "Prk PARIS");
    }

    @Test
    public void testGetId() {
        assertEquals(parking.getId(), new Integer(1));
    }

    @Test
    public void testGetName() {
        assertEquals(parking.getName(), "Prk PARIS");
    }

    @Test
    public void testSetName() {
        parking.setName("Prk MARSEILLE");
        assertEquals(parking.getName(), "Prk MARSEILLE");
    }

    // Also tests newParkingSpot(
    @Test
    public void testCountParkingSpots() {
        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory() {
            int i = 0;
            @Override
            public void setIdProvider(ParkingSpotIdProvider provider) {

            }

            @Override
            public ParkingSpot createParkingSpot() {
                ParkingSpot parkingSpot = mock(ParkingSpot.class);
                when(parkingSpot.getId()).thenReturn(++i);
                return parkingSpot;
            }
        };

        assertEquals(new Integer(0), parking.countParkingSpots());
        parking.newParkingSpot(parkingSpotFactory);

        assertEquals(new Integer(1), parking.countParkingSpots());
        parking.newParkingSpot(parkingSpotFactory, 42);

        assertEquals(new Integer(43), parking.countParkingSpots());
        parking.newParkingSpot(parkingSpotFactory);
    }

    @Test
    public void testCountParkingSpotsPredicate() {
        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory() {
            int i = 0;
            @Override
            public void setIdProvider(ParkingSpotIdProvider provider) {

            }

            @Override
            public ParkingSpot createParkingSpot() {
                ParkingSpot parkingSpot = mock(ParkingSpot.class);
                when(parkingSpot.getId()).thenReturn(++i);
                return parkingSpot;
            }
        };

        parking.newParkingSpot(parkingSpotFactory, 42);
        assertEquals(new Integer(42 / 2), parking.countParkingSpots(parkingSpot -> parkingSpot.getId() <= 42 / 2));
        assertEquals(new Integer(parking.countParkingSpots() / 2), parking.countParkingSpots(parkingSpot -> parkingSpot.getId() <= 42 / 2));
    }

    @Test
    public void testGetSpotBySpotId() {
        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory() {
            int i = 451;
            @Override
            public void setIdProvider(ParkingSpotIdProvider provider) {

            }

            @Override
            public ParkingSpot createParkingSpot() {
                ParkingSpot parkingSpot = mock(ParkingSpot.class);
                when(parkingSpot.getId()).thenReturn(++i);
                return parkingSpot;
            }
        };

        ParkingSpot parkingSpot = parking.newParkingSpot(parkingSpotFactory);
        assertEquals(parkingSpot, parking.getSpotBySpotId(452));
    }

    @Test
    public void testGetSpotByVehiclePlate() {
        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory() {
            int i = 0;
            @Override
            public void setIdProvider(ParkingSpotIdProvider provider) {

            }

            @Override
            public ParkingSpot createParkingSpot() {
                ParkingSpot parkingSpot = mock(ParkingSpot.class);
                when(parkingSpot.getId()).thenReturn(++i);
                when(parkingSpot.isVehicleParked()).thenReturn(true);
                when(parkingSpot.getVehicle()).thenAnswer(invocationOnMock -> {
                    Vehicle vehicle = mock(Vehicle.class);
                    Integer id=parkingSpot.getId();
                    when(vehicle.getPlate()).thenReturn(id.toString());
                    return vehicle;
                });
                return parkingSpot;
            }
        };

        parking.newParkingSpot(parkingSpotFactory, 42);
        assertEquals(parking.getSpotBySpotId(34), parking.getSpotByVehiclePlate("34"));
    }
}