package presentation;

import businessLayer.Management.ClientManagement;
import model.Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * <p>
 *     Interfata grafica pentru fereastra de client
 * </p>
 * @author Vlad-Andrei Balcanu
 */
public class ClientGUI {
    private static ClientManagement clientManagement;
    private JFrame frame;
    private JTextField clientIdTextField;
    private JTextField clientNameTextField;
    private JTextField clientAgeTextField;
    private JTextField clientEmailTextField;
    private JPanel clients;
    private JPanel orders;
    private JPanel products;
    private JTable clientsTable;

    /**
     * Constructorul interfetei grafice
     */
    public ClientGUI() {

        initialize();
    }

    /**
     * Metoda de initializare a componentelor interfetei grafice
     */
    private void initialize() {
        frame = new JFrame("clients");
        frame.setBounds(100, 100, 550, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        clientManagement = ClientManagement.getInstance();
        frame.setVisible(true);


        final JPanel clients = new JPanel();
        frame.getContentPane().add(clients, "name_198875280232200");
        clients.setLayout(null);
        clients.setVisible(true);

        clientIdTextField = new JTextField();
        clientIdTextField.setToolTipText("");
        clientIdTextField.setText("Introduce Client ID");
        clientIdTextField.setBounds(20, 353, 183, 34);
        clients.add(clientIdTextField);
        clientIdTextField.setColumns(10);

        JButton searchClientsById = new JButton("Find Client");
        searchClientsById.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int clientId=Integer.parseInt(clientIdTextField.getText());
                Object[] row=new Object[4];
                DefaultTableModel model1=(DefaultTableModel) clientsTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                clientsTable.setModel(model1);
                try {
                    Client client=clientManagement.findClientById(clientId);
                    row[0]=client.getId();
                    row[1]=client.getName();
                    row[2]=client.getAge();
                    row[3]=client.getEmail();
                    model1.addRow(row);

                    System.out.println("We found : "+client.getId()+" " +client.getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        searchClientsById.setBounds(20, 410, 183, 54);
        clients.add(searchClientsById);

        clientNameTextField = new JTextField();
        clientNameTextField.setText("Introduce Client Name");
        clientNameTextField.setBounds(238, 353, 183, 34);
        clients.add(clientNameTextField);
        clientNameTextField.setColumns(10);

        clientAgeTextField = new JTextField();
        clientAgeTextField.setText("Introduce Client Age");
        clientAgeTextField.setBounds(238, 430, 183, 34);
        clients.add(clientAgeTextField);
        clientAgeTextField.setColumns(10);

        clientEmailTextField = new JTextField();
        clientEmailTextField.setText("Introduce Client Email");
        clientEmailTextField.setBounds(238, 500, 183, 34);
        clients.add(clientEmailTextField);
        clientEmailTextField.setColumns(10);

        JButton insertClientButton = new JButton("Insert Client");
        insertClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.setEmail(clientEmailTextField.getText());
                client.setName(clientNameTextField.getText());
                client.setAge(Integer.parseInt(clientAgeTextField.getText()));
                try {
                    clientManagement.addClient(client);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        insertClientButton.setBounds(238, 577, 183, 54);
        clients.add(insertClientButton);

        JButton deleteClientButton = new JButton("Delete Client");
        deleteClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int clientId=Integer.parseInt(clientIdTextField.getText());
                try {
                   clientManagement.deleteClient(clientId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        deleteClientButton.setBounds(20, 490, 183, 55);
        clients.add(deleteClientButton);
        clientsTable = new JTable();
        clientsTable.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Client","Nume","Varsta","Email"}));


        clientsTable.setBounds(10, 11, 484, 314);
        clients.add(clientsTable);

        JButton showAllClientsButton = new JButton("Show Clients");
        showAllClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] row=new Object[4];
                DefaultTableModel model1=(DefaultTableModel) clientsTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                clientsTable.setModel(model1);
                try {
                    List<Client> clients;
                    clients=clientManagement.viewAllClients();
                    for(Client cl: clients){
                        System.out.println(cl.getId()+" " +cl.getName());
                        row[0]=cl.getId();
                        row[1]=cl.getName();
                        row[2]=cl.getAge();
                        row[3]=cl.getEmail();
                        model1.addRow(row);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        showAllClientsButton.setBounds(122, 655, 183, 54);
        clients.add(showAllClientsButton);

        JButton updateClientButton = new JButton("Update Client");
        updateClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                int clientId=Integer.parseInt(clientIdTextField.getText());
                client.setEmail(clientEmailTextField.getText());
                client.setName(clientNameTextField.getText());
                client.setAge(Integer.parseInt(clientAgeTextField.getText()));
                try {
                    clientManagement.updateClient(clientId,client);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        updateClientButton.setBounds(20, 577, 183, 54);
        clients.add(updateClientButton);
        DefaultTableModel model1=(DefaultTableModel) clientsTable.getModel();
        Object[] row=new Object[4];
        row[0]="Client ID";
        row[1]="Client Name";
        row[2]="Client Age";
        row[3]="Client Email";
        model1.addRow(row);

    }
}
