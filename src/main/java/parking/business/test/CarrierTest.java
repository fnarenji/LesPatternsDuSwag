package parking.business.test;

import org.junit.Test;
import parking.business.logic.Carrier;
import parking.business.vehicles.Car;
import parking.business.vehicles.Vehicle;

import static org.junit.Assert.*;

public class CarrierTest {

    @Test
    public void testEmpty() throws Exception {
        Carrier C = new Carrier();
        assertEquals(C.empty(),true);
    }

    @Test
    public void testCanFit() throws Exception {
        Carrier C = new Carrier();
        Vehicle V = new Car();
        assertEquals(C.canFit(V), true);
    }

    @Test
    public void testPark() throws Exception {
        Carrier C = new Carrier();
        Vehicle V = new Car();
        C.park(V);
        assertEquals(C.empty(), false);
    }

    @Test
    public void testUnpark() throws Exception {
        Carrier C = new Carrier();
        Vehicle V = new Car();
        C.park(V);
        assertEquals(C.empty(), false);
        C.unpark();
        assertEquals(C.empty(), true);
    }

    @Test
    public void testSetBooked() throws Exception {
        Carrier C = new Carrier();
        C.setBooked(true);
        assertEquals(C.getBooked(), true);
    }

    @Test
    public void testSetId() throws Exception {
        Carrier C = new Carrier();
        C.setId(new Integer(1));
        assertEquals(C.getId(),new Integer(1));
    }

}