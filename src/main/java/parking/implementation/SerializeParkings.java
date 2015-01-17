package parking.implementation;

import parking.api.business.concrete.Parking;

import java.io.*;

/**
 * Created by thomasmunoz on 17/01/15.
 */
public class SerializeParkings {

    public static void serialize(Parking parking) {
        ObjectOutputStream oos = null;
        try {

            OutputStream file = new FileOutputStream("save/parking.ser");
            oos =  new ObjectOutputStream(file);

            oos.writeObject(parking);
            oos.flush();

        } catch (FileNotFoundException | NotSerializableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Parking deserialize() {
        ObjectInputStream ois = null;
        Parking parking = null;

            InputStream file = null;
            try {
                file = new FileInputStream("save/parking.ser");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ois = new ObjectInputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                parking = (Parking) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return parking;
    }
}
