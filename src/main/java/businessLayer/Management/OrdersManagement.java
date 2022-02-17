package businessLayer.Management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Client;
import model.Product;
import model.Orders;
import dataAccessLayer.ParametricDAO;

/**
 * @author Vlad-Andrei Balcanu
 */
public class OrdersManagement {

    public static ParametricDAO<Orders> orderDAO;
    public static OrdersManagement ordersManagement = null;
    public static ProductManagement productManagement = ProductManagement.getInstance();
    public static ClientManagement clientManagement = ClientManagement.getInstance();
    private static int bills=0;
    /**
     * Constructorul clasei
     */

    private OrdersManagement() {
        OrdersManagement.orderDAO = new ParametricDAO<>(Orders.class);
    }
    /**
     * methode pentru a luat instanca
     */

    public static OrdersManagement getInstance() {
        if (ordersManagement == null) {
            ordersManagement = new OrdersManagement();
        }
        return ordersManagement;
    }

    /**
     * <p>
     *     Aceasta Metoda imi cauta o anumita comanda in tabela dupa id
     * </p>
     * @param id Cauta o camanda dupa id-ul dat de noi
     * @return imi returneaza un obiect de tipul Orders daca a fost gasit
     * @throws SQLException arunca exceptie SQL daca am introdus un id care nu exista in tabela
     */
    public Orders findOrderById(int id) throws SQLException {
        return orderDAO.findById(id);
    }

    /**
     * <p><
     * Aceasta Metoda imi actualizeaza o camanda existenta
     * </p>
     * @param id Id-ul comenzii pe care dorim sa o actualizam
     * @param orders Un obiect de tip orders care contine toate atributele noi
     * @throws SQLException arunca o excpetie de tip SQL daca comanda nu exista in tabela
     */
    public void updateOrder(int id, Orders orders) throws SQLException {
        Client client= clientManagement.findClientById(orders.getIdClient());
        Product product= productManagement.findProductById(orders.getIdProduct());
        Orders order=orderDAO.findById(id);
        Product oldProduct= productManagement.findProductById(order.getIdProduct());
        oldProduct.setStock(oldProduct.getStock()+order.getNrOfProducts());

        if(client!=null && product !=null) {
            Product productToBeUpdated = productManagement.findProductById(orders.getIdProduct());
            int remainingStock = productToBeUpdated.getStock() - orders.getNrOfProducts();
            productToBeUpdated.setStock(remainingStock);
            if(remainingStock>=0) {
                productManagement.updateProduct(oldProduct.getId(),oldProduct);
                productManagement.updateProduct(productToBeUpdated.getId(), productToBeUpdated);
                orders.setTotalCost(orders.getNrOfProducts() * productToBeUpdated.getValue());
                orderDAO.update(id, orders);
            }else
            {
                System.out.println("UNDER STOCK");
            }
        }
        else
        {
            System.out.println("Either the product or the client doesn't exist");
        }
    }

    /**
     * <p>
     *     Aceasta metoda imi adauga o noua comanda in tabela
     * </p>
     * @param orders Obiect de tip orders pe care il adaugam in tabela
     * @throws SQLException arunca o exceptie SQL daca clientul sau produsul nu sunt existente
     */
    public void addOrder(Orders orders) throws SQLException {
        Client client= clientManagement.findClientById(orders.getIdClient());
        Product product= productManagement.findProductById(orders.getIdProduct());
        if(client!=null && product !=null) {
            Product productToBeUpdated = productManagement.findProductById(orders.getIdProduct());
            int remainingStock = productToBeUpdated.getStock() - orders.getNrOfProducts();
            productToBeUpdated.setStock(remainingStock);
            if (remainingStock >= 0) {
                productManagement.updateProduct(productToBeUpdated.getId(), productToBeUpdated);
                orders.setTotalCost(orders.getNrOfProducts() * productToBeUpdated.getValue());
                orderDAO.add(orders);
            } else {
                System.out.println("UNDER STOCK");
            }
        }else
        {
            System.out.println("Either the product or the client doesn't exist");
        }
}

    /**
     * <p>
     *     Metoda prin care stergem o comanda dorita
     * </p>
     * @param id Id-ul comenzii pe care dorim sa o stergem
     * @throws SQLException arunca o exceptie SQL daca nu exita comanda in tabela
     */
    public void deleteOrder(int id) throws SQLException {
        orderDAO.delete(id);
    }

    /**
     * <p>
     *     Metoda folosita pentru a afisa toate obiectele de tip orders (toate comenzile)
     * </p>
     * @return ne returneaza o lista de comenzi
     * @throws SQLException
     */
    public List<Orders> viewOrders() throws SQLException {
        return orderDAO.showAll();
    }

    /**
     * <p>
     *     Metoda prin care generam chitanta la fiecare comanda
     * </p>
     * @param orders Comanda (Obiectul de tip Orders) pentru care generam chitanta
     */
    public void generateBill(Orders orders){
        String billName="bill";
        bills++;
        billName=billName+bills+".txt";
        try{
            FileWriter writer= new FileWriter(billName,true);
            Client client=clientManagement.findClientById(orders.getIdClient());
            Product product=productManagement.findProductById(orders.getIdProduct());
            orders.setTotalCost(orders.getNrOfProducts()*product.getValue());
            writer.write("BILL/RECEIPT NUMBER "+ bills+"\n");
            writer.write("Client's name : "+client.getName()+"\n"+"Client's email : "+client.getEmail()+"\n");
            writer.write("Acquired product : "+product.getName()+"\nCostPerProduct : "+product.getValue()+"\n");
            writer.write("Number of products acquired : "+orders.getNrOfProducts()+"\n");
            writer.write("Total Cost : "+orders.getTotalCost());

            writer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

}
