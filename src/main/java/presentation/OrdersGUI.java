package presentation;

import businessLayer.Management.OrdersManagement;
import model.Orders;

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
 *     Interfata grafica pentru fereastra de comenzi
 * </p>
 * @author Vlad-Andrei Balcanu
 *
 */

public class OrdersGUI {
    private static OrdersManagement ordersManagement;
    private JFrame frame;
    private JTextField ordersIdTextField;
    private JTextField cIdTextField;
    private JTextField pIdTextField;
    private JTextField nrOfProductsTextField;
    private JTable ordersTable;

    /**
     * <p>Constructorul interfetei grafice</p>
     */
    public OrdersGUI() {

        initialize();
    }

    /**
     * <p>
     *     Metoda pentru initializarea componentelor interfetei
     * </p>
     */

    private void initialize() {
        frame = new JFrame("orders");
        frame.setBounds(635, 100, 550, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        ordersManagement=OrdersManagement.getInstance();
        frame.setVisible(true);

        final JPanel orders = new JPanel();
        frame.getContentPane().add(orders, "name_198880099375100");
        orders.setLayout(null);
        orders.setVisible(false);

        ordersIdTextField = new JTextField();
        ordersIdTextField.setText("Introduce Order ID");
        ordersIdTextField.setBounds(10, 382, 221, 29);
        orders.add(ordersIdTextField);
        ordersIdTextField.setColumns(10);

        JButton searchOrdersById = new JButton("Find Order");
        searchOrdersById.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int orderId= Integer.parseInt(ordersIdTextField.getText());
                Object[] row=new Object[5];
                DefaultTableModel model1=(DefaultTableModel) ordersTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                try {
                    Orders orders=ordersManagement.findOrderById(orderId);
                    row[0]=orders.getId();
                    row[1]=orders.getIdClient();
                    row[2]=orders.getIdProduct();
                    row[3]=orders.getNrOfProducts();
                    row[4]=orders.getTotalCost();
                    model1.addRow(row);
                    System.out.println(orders.getId()+ " " +orders.getTotalCost());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        searchOrdersById.setBounds(54, 445, 137, 45);
        orders.add(searchOrdersById);

        cIdTextField = new JTextField();
        cIdTextField.setText("Introduce Client ID");
        cIdTextField.setBounds(254, 382, 221, 29);
        orders.add(cIdTextField);
        cIdTextField.setColumns(10);

        pIdTextField = new JTextField();
        pIdTextField.setText("Introduce Product ID");
        pIdTextField.setBounds(254, 453, 221, 29);
        orders.add(pIdTextField);
        pIdTextField.setColumns(10);

        nrOfProductsTextField = new JTextField();
        nrOfProductsTextField.setText("Introduce Number of Products");
        nrOfProductsTextField.setBounds(254, 528, 221, 29);
        orders.add(nrOfProductsTextField);
        nrOfProductsTextField.setColumns(10);

        JButton addOrderButton = new JButton("Place Order");
        addOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Orders orders=new Orders();
                orders.setIdClient(Integer.parseInt(cIdTextField.getText()));
                orders.setIdProduct(Integer.parseInt(pIdTextField.getText()));
                orders.setNrOfProducts(Integer.parseInt(nrOfProductsTextField.getText()));
                try {
                    ordersManagement.addOrder(orders);
                    ordersManagement.generateBill(orders);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        addOrderButton.setBounds(293, 614, 140, 45);
        orders.add(addOrderButton);

        JButton deleteOrderButton = new JButton("Delete Order");
        deleteOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int orderId=Integer.parseInt(ordersIdTextField.getText());
                try {
                    ordersManagement.deleteOrder(orderId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        deleteOrderButton.setBounds(54, 528, 137, 45);
        orders.add(deleteOrderButton);

        JButton showOrdersButton = new JButton("Show Orders");
        showOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Orders> orders;
                Object[] row=new Object[5];
                DefaultTableModel model1=(DefaultTableModel) ordersTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                try {
                    orders=ordersManagement.viewOrders();
                    for(Orders or :orders){
                        row[0]=or.getId();
                        row[1]=or.getIdClient();
                        row[2]=or.getIdProduct();
                        row[3]=or.getNrOfProducts();
                        row[4]=or.getTotalCost();
                        model1.addRow(row);
                        System.out.println(or.getId()+" " +or.getTotalCost());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        showOrdersButton.setBounds(149, 684, 140, 45);
        orders.add(showOrdersButton);

        ordersTable = new JTable();
        ordersTable.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Order","Client","Product","NrOfProducts","TotalCost"}));
        ordersTable.setShowGrid(true);
        ordersTable.setBounds(10, 14, 464, 344);
        orders.add(ordersTable);

        JButton updateOrderButton = new JButton("Update Order");
        updateOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Orders orders=new Orders();
                int ordersId=Integer.parseInt(ordersIdTextField.getText());
                orders.setNrOfProducts(Integer.parseInt(nrOfProductsTextField.getText()));
                orders.setIdProduct(Integer.parseInt(pIdTextField.getText()));
                orders.setIdClient(Integer.parseInt(cIdTextField.getText()));
                try {
                    ordersManagement.updateOrder(ordersId,orders);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        updateOrderButton.setBounds(54, 614, 137, 45);
        orders.add(updateOrderButton);
        DefaultTableModel model1=(DefaultTableModel) ordersTable.getModel();
        Object[] row=new Object[5];
        row[0]="Order ID";
        row[1]="Client ID";
        row[2]="Product ID";
        row[3]="Nr of Products";
        row[4]="Total Cost";
        model1.addRow(row);
    }
}
