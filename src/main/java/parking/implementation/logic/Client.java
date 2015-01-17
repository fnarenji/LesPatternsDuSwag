package parking.implementation.logic;

/**
 * Created by  on 14/01/15.
 */
public class Client {

    private String civility;
    private String lastName;
    private String firstName;

    public Client(String civility, String lastName, String firstName) {
        this.civility = civility;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getCivility() {
        return civility;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
