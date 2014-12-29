package parking.business;

import org.junit.Before;
import org.junit.Test;
import parking.exceptions.ParkingBookedSpotsExceptions;
import parking.exceptions.ParkingExistsException;
import parking.exceptions.ParkingNotPresentException;

import static org.junit.Assert.assertEquals;

public class ParkingTest {
    Parking parking;

    @Before
    public void setUp() throws ParkingExistsException, ParkingBookedSpotsExceptions, ParkingNotPresentException {
        ParkingManager parkingManager = ParkingManager.getInstance();
        if (parking != null)
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

    @Test
    public void testCountParkingSpots() {
        ParkingSpotFactory parkingSpotFactory;


    }

    @Test
    public void testCountParkingSpotsPredicate() {

    }

    @Test
    public void testNewParkingSpot() {

    }

    @Test
    public void testNewParkingSpotAmount() {

    }

    @Test
    public void testGetSpotBySpotId() {

    }

    @Test
    public void testGetSpotByVehiclePlate() {

    }
}