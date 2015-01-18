package parking.api.business.parking;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotFactory;
import parking.api.business.parkingspot.ParkingSpotIdProvider;
import parking.api.exceptions.ParkingBookedSpotsExceptions;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.business.parkingspot.CarParkingSpot;

import static org.junit.Assert.*;

public class ParkingManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ParkingManager parkingManager;

    @Before
    public void resetSingleton() {
        ParkingManager.resetSingleton();
        parkingManager = ParkingManager.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(parkingManager);
    }

    @Test
    public void testGetSetCompanyName() {
        final String companyName = "MY COMPANY NAME é@uø";
        parkingManager.setCompanyName(companyName);
        assertEquals(companyName, parkingManager.getCompanyName());
    }

    @Test
    public void testNewParking() throws ParkingExistsException, ParkingNotPresentException {
        parkingManager.newParking(1, "Prk MARSEILLE");
        thrown.expect(ParkingExistsException.class);
        parkingManager.newParking(1, "Prk MARSEILLE2");
    }

    @Test
    public void testDeleteParking() throws ParkingExistsException, ParkingNotPresentException, ParkingBookedSpotsExceptions {
        Parking parking = parkingManager.newParking(1, "Prk AIX-EN-PCE");

        // @todo Add booking tests.
        parkingManager.deleteParking(parking.getId());

        try {
            parkingManager.getParkingById(parking.getId());
            fail("Exception not thrown");
        } catch (ParkingNotPresentException e) {
        }

        try {
            parkingManager.deleteParking(parking.getId());
            fail("Exception not thrown");
        } catch (ParkingNotPresentException e) {
        }

        parking = parkingManager.newParking(1, "Prk GAP");
        assertEquals(parkingManager.getParkingById(1), parking);
    }

    @Test
    public void testContainsParking() throws Exception {
        assertFalse(parkingManager.containsParking(1));
        parkingManager.newParking(1, "Prk AIX-EN-PCE");
        assertTrue(parkingManager.containsParking(1));
    }

    @Test
    public void testGetParkingById() throws ParkingNotPresentException, ParkingExistsException {
        Parking parking = parkingManager.newParking(1, "Prk AVIGNON");
        assertEquals(parking, parkingManager.getParkingById(1));

        thrown.expect(ParkingNotPresentException.class);
        parkingManager.getParkingById(Integer.MAX_VALUE);
    }

    @Test
    public void testSerializeParking() {
        try {
            parkingManager.newParking(1, "Parking du SWAG");
            try {
                parkingManager.getParkingById(1).newParkingSpot(new ParkingSpotFactory() {
                    int i = 0;

                    @Override
                    public void setIdProvider(ParkingSpotIdProvider provider) {

                    }
                    @Override
                    public ParkingSpot createParkingSpot() {
                        ParkingSpot parkingSpot = new CarParkingSpot(1);
                        return parkingSpot;
                    }
                });
            } catch (ParkingNotPresentException e) {
                e.printStackTrace();
            }
        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }
        ParkingManagerSerializer.serialize();
        ParkingManagerSerializer.deserialize("save/parkinManager.ser");

        assertEquals(ParkingManager.getInstance().containsParking(1), true);
        try {
            assertEquals(ParkingManager.getInstance().getParkingById(1).countParkingSpots() == 1, true);
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }
}