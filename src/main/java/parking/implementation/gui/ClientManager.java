package parking.implementation.gui;

import parking.implementation.business.Client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by sknz on 1/17/15.
 * <p>
 * Groups all the functions which manage the clients
 */
public class ClientManager implements Iterable<Client> {
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

    @Override
    public Iterator<Client> iterator() {
        return clients.iterator();
    }

    @Override
    public void forEach(Consumer<? super Client> action) {
        clients.forEach(action);
    }
}
