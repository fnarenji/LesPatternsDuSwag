package parking.implementation.gui;

import parking.implementation.logic.Client;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by sknz on 1/17/15.
 */
public class ClientManager {
    private static ClientManager instance = new ClientManager();
    private Collection<Client> clients = new HashSet<>();

    private ClientManager() {
        clients.add(new Client("Mr.", "Granhaqueurre", "Anonymous"));
    }

    public static ClientManager getInstance() {
        return instance;
    }

    public void addClient(Client e) {
        clients.add(e);
    }

    public void removeClient(Client e) {
        clients.remove(e);
    }

    public int count() {
        return clients.size();
    }
}
