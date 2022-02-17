package businessLayer.Management;

import model.Client;

import java.sql.SQLException;
import java.util.List;
import dataAccessLayer.ParametricDAO;

/***
 * @author Vlad-Andrei Balcanu
 */
public class ClientManagement {

    public static ParametricDAO<Client> clientDAO;
    public static ClientManagement clientManagement =null ;
    /**
     * <p>
     *     Constructorul clasei
     * </p>
     */
    private ClientManagement() {
        ClientManagement.clientDAO = new ParametricDAO<>(Client.class);
    }

    /**
     *<p>
     *     Metoda pentru a genera instanta obiectului
     *</p>
     * @return returneaza o instanta singleton de clientManagement
     */
    public static ClientManagement getInstance() {
        if (clientManagement == null) {
            clientManagement = new ClientManagement();
        }
        return clientManagement;
    }
    /**
     *  <p>
     *      Metoda de cautare a unui client in tabela
     *  </p>
     * @param id id-ul clientului cautat
     * @return un obiect de tipul Client cu clientul cautat
     * @throws SQLException arunca o exceptie daca clientul nu exista
     */
    public Client findClientById(int id) throws SQLException {
        return clientDAO.findById(id);
    }

    /**
     * <p>
     *     Metoda de actualizare a unui client din tabela
     * </p>
     * @param id id-ul clientului cautat
     * @param client obiectul de tip client ce contine datele cu care sa va actualiza actualul client
     * @throws SQLException arunca o exceptie daca nu exista clientul in tabela
     */

    public void updateClient(int id, Client client) throws SQLException {
         clientDAO.update(id, client);
    }

    /**
     * <p>
     *     Metoda de adaugare a unui client in tabela
     * </p>
     * @param client obiect de tip client ce va fi adaugat in tabela
     * @throws SQLException
     */

    public void addClient(Client client) throws SQLException {
        clientDAO.add(client);
    }

    /**
     * <p>
     *     Metoda de stergere a unui client din tabela
     * </p>
     * @param id id-ul clientului ce trebuie sters
     * @throws SQLException arunca o exceptie daca nu exista clientul
     */

    public void deleteClient(int id) throws SQLException {
         clientDAO.delete(id);
    }

    /**
     * <p>
     *     Metoda folosita pentru a afisa toti clientii din tabela
     * </p>
     * @return o lista de obiecte de tipul Client
     * @throws SQLException
     */

    public List<Client> viewAllClients() throws SQLException {
        return clientDAO.showAll();
    }
}