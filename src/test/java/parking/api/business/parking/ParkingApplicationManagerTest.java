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

public class ParkingApplicationManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ParkingApplicationManager parkingApplicationManager;

    @Before
    public void resetSingleton() {
        ParkingApplicationManager.resetSingleton();
        parkingApplicationManager = ParkingApplicationManager.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(parkingApplicationManager);
    }

    @Test
    public void testGetSetCompanyName() {
        final String companyName = "MY COMPANY NAME é@uø";
        parkingApplicationManager.setCompanyName(companyName);
        assertEquals(companyName, parkingApplicationManager.getCompanyName());
    }

    @Test
    public void testNewParking() throws ParkingExistsException, ParkingNotPresentException {
        parkingApplicationManager.newParking(1, "Prk MARSEILLE");
        thrown.expect(ParkingExistsException.class);
        parkingApplicationManager.newParking(1, "Prk MARSEILLE2");
    }

    @Test
    public void testDeleteParking() throws ParkingExistsException, ParkingNotPresentException, ParkingBookedSpotsExceptions {
        Parking parking = parkingApplicationManager.newParking(1, "Prk AIX-EN-PCE");

        // @todo Add booking tests.
        parkingApplicationManager.deleteParking(parking.getId());

        try {
            parkingApplicationManager.getParkingById(parking.getId());
            fail("Exception not thrown");
        } catch (ParkingNotPresentException e) {
        }

        try {
            parkingApplicationManager.deleteParking(parking.getId());
            fail("Exception not thrown");
        } catch (ParkingNotPresentException e) {
        }

        parking = parkingApplicationManager.newParking(1, "Prk GAP");
        assertEquals(parkingApplicationManager.getParkingById(1), parking);
    }

    @Test
    public void testContainsParking() throws Exception {
        assertFalse(parkingApplicationManager.containsParking(1));
        parkingApplicationManager.newParking(1, "Prk AIX-EN-PCE");
        assertTrue(parkingApplicationManager.containsParking(1));
    }

    @Test
    public void testGetParkingById() throws ParkingNotPresentException, ParkingExistsException {
        Parking parking = parkingApplicationManager.newParking(1, "Prk AVIGNON");
        assertEquals(parking, parkingApplicationManager.getParkingById(1));

        thrown.expect(ParkingNotPresentException.class);
        parkingApplicationManager.getParkingById(Integer.MAX_VALUE);
    }

    @Test
    public void testSerializeParking() {
        try {
            parkingApplicationManager.newParking(1, "Parking du SWAG");
            try {
                parkingApplicationManager.getParkingById(1).newParkingSpot(new ParkingSpotFactory() {
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
        ParkingManagerSerializer.serialize("save/parkingManager.ser");
        ParkingManagerSerializer.deserialize("save/parkingManager.ser");

        assertEquals(ParkingApplicationManager.getInstance().containsParking(1), true);
        try {
            assertEquals(ParkingApplicationManager.getInstance().getParkingById(1).countParkingSpots() == 1, true);
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }
}