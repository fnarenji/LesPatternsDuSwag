package parking.implementation.business;

import parking.api.business.parking.ParkingManager;

import java.io.*;

/**
 * Created by thomasmunoz on 17/01/15.
 */
public class SerializeParkingManager {

    /**
     * Serialize a Parking Manager
     * @param parkingManager
     */
    public static void serialize(ParkingManager parkingManager) {
        ObjectOutputStream oos = null;
        try {

            OutputStream file = new FileOutputStream("save/parkingManager.ser");
            oos = new ObjectOutputStream(file);

            oos.writeObject(parkingManager);
            oos.flush();

        } catch (FileNotFoundException | NotSerializableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deserialize a ParkingManager object stored in save/parkingManager.ser
     * @return ParkingManager contained in save/parkingManager.ser
     */
    public static ParkingManager deserialize() {
        ObjectInputStream ois = null;
        ParkingManager parkingManager = null;

        InputStream file = null;
        try {
            file = new FileInputStream("save/parkingManager.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois = new ObjectInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            parkingManager = (ParkingManager) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return parkingManager;
    }
}
