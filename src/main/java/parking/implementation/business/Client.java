package parking.implementation.business;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;

        return true;
    }
}
