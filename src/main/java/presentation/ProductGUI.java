package presentation;

import businessLayer.Management.ProductManagement;
import model.Product;

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
 *     Interfata grafica pentru fereastra de produse
 * </p>
 * @author Vlad-Andrei Balcanu
 */

public class ProductGUI {
    private static ProductManagement productManagement;
    private JFrame frame;
    private JPanel clients;
    private JPanel orders;
    private JPanel products;
    private JTable productsTable;
    private JTextField productIdTextField;
    private JTextField valueTextField;
    private JTextField nameTextField;
    private JTextField stockTextField;

    /**
     * <p>
     *     Constructorul interfetei grafice
     * </p>
     */
    public ProductGUI() {

        initialize();
    }

    /**
     * Metoda pentru initializarea componentelor interfetei grafice
     */
    private void initialize() {
        frame = new JFrame("products");
        frame.setBounds(1170, 100, 550, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        productManagement=ProductManagement.getInstance();
        frame.setVisible(true);

        final JPanel products = new JPanel();
        frame.getContentPane().add(products, "name_198884147699100");
        products.setLayout(null);
        products.setVisible(false);

        productsTable = new JTable();
        productsTable.setBounds(10, 11, 483, 406);
        productsTable.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Product","Name","Value","Stock"}));
        products.add(productsTable);


        productIdTextField = new JTextField();
        productIdTextField.setText("Introduce Product ID");
        productIdTextField.setBounds(10, 444, 226, 32);
        products.add(productIdTextField);
        productIdTextField.setColumns(10);

        JButton findProductButton = new JButton("Find Product");
        findProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int productId=Integer.parseInt(productIdTextField.getText());
                Object[] row=new Object[4];
                DefaultTableModel model1=(DefaultTableModel) productsTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                try {
                    Product product=productManagement.findProductById(productId);
                    row[0]=product.getId();
                    row[1]=product.getName();
                    row[2]=product.getValue();
                    row[3]=product.getStock();
                    model1.addRow(row);
                    System.out.println(product.getId()+" "+product.getValue()+" "+product.getStock());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        findProductButton.setBounds(50, 519, 137, 46);
        products.add(findProductButton);

        JButton deleteProductButton = new JButton("Delete Product");
        deleteProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int productId=Integer.parseInt(productIdTextField.getText());
                try {
                    productManagement.deleteProduct(productId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        deleteProductButton.setBounds(50, 612, 135, 46);
        products.add(deleteProductButton);

        valueTextField = new JTextField();
        valueTextField.setText("Introduce Product Value");
        valueTextField.setBounds(267, 444, 226, 32);
        products.add(valueTextField);
        valueTextField.setColumns(10);

        nameTextField = new JTextField();
        nameTextField.setText("Introduce Product Name");
        nameTextField.setBounds(267, 500, 226, 32);
        products.add(nameTextField);
        nameTextField.setColumns(10);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Product product=new Product();
                product.setName(nameTextField.getText());
                product.setStock(Integer.parseInt(stockTextField.getText()));
                product.setValue(Integer.parseInt(valueTextField.getText()));
                try {
                    productManagement.addProduct(product);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        addProductButton.setBounds(305, 612, 137, 46);
        products.add(addProductButton);

        JButton showAllProductsButton = new JButton("Show Products");
        showAllProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] row=new Object[4];
                DefaultTableModel model1=(DefaultTableModel) productsTable.getModel();
                int rowCount=model1.getRowCount();
                for(int i =rowCount-1;i>=1;i--){
                    model1.removeRow(i);
                }
                List<Product> productList;
                try {

                    productList=productManagement.viewAllProducts();
                    for(Product pr:productList){
                        row[0]=pr.getId();
                        row[1]=pr.getName();
                        row[2]=pr.getValue();
                        row[3]=pr.getStock();
                        model1.addRow(row);
                        System.out.println(pr.getId()+" " +pr.getName());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        showAllProductsButton.setBounds(50, 693, 137, 46);
        products.add(showAllProductsButton);

        JButton updateProductButton = new JButton("Update Product");
        updateProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Product product= new Product();
                int productId=Integer.parseInt(productIdTextField.getText());
                product.setName(nameTextField.getText());
                product.setValue(Integer.parseInt(valueTextField.getText()));
                product.setStock(Integer.parseInt(stockTextField.getText()));
                try {
                    productManagement.updateProduct(productId,product);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        updateProductButton.setBounds(305, 693, 137, 46);
        products.add(updateProductButton);

        stockTextField = new JTextField();
        stockTextField.setText("Introduce Stock");
        stockTextField.setBounds(267, 561, 226, 32);
        products.add(stockTextField);
        stockTextField.setColumns(10);

        DefaultTableModel model1=(DefaultTableModel) productsTable.getModel();
        Object[] row=new Object[4];
        row[0]="Product ID";
        row[1]="Product Name";
        row[2]="Product Value";
        row[3]="Product Stock";
        model1.addRow(row);
    }
}
