package businessLayer.Management;

import dataAccessLayer.ParametricDAO;
import model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Vlad-Andrei Balcanu
 */
public class ProductManagement {


    public static ParametricDAO<Product> productDAO;

    public static ProductManagement productManagement = null;

    /**
     * <p>
     *     Constructorul clasei
     * </p>
     */
    private ProductManagement() {
        productDAO = new ParametricDAO<>(Product.class);
    }

    /**
     * <p>
     *     Metoda pentru a lua instanta obiectului
     * </p>
     * @return un obiect de tip ProductManagement
     */
    public static ProductManagement getInstance() {
        if (productManagement == null) {
            productManagement = new ProductManagement();
        }
        return productManagement;
    }

    /**
     * <p>
     *     Metoda pentru gasi un produs dupa id
     * </p>
     * @param id Id-ul produsului cautat
     * @return Un obiect de tipul Product
     * @throws SQLException Arunca o exceptie daca nu exista produsul
     */
    public Product findProductById(int id) throws SQLException {
        return productDAO.findById(id);
    }

    /**
     * <p>
     *     Metoda pentru actualizarea unui produs existent
     *     </p>
     * @param id id-ul produsului ce trebuie actualizat
     * @param product Obiect de tip product ce contine noile caracteristici
     * @throws SQLException arunca o exceptie daca nu exista produsul
     */

    public void updateProduct(int id, Product product) throws SQLException {
        productDAO.update(id, product);
    }

    /**
     * <p>
     *     Metoda pentru adaugarea unui nou produs
     * </p>
     * @param product Obiect de tip product ce trebuie adaugat
     * @throws SQLException
     */

    public void addProduct(Product product) throws SQLException {
         productDAO.add(product);
    }

    /**
     * <p>
     *     Metoda pentru a sterge un produs
     * </p>
     * @param id Id-ul produsului ce trebuie sters
     * @throws SQLException arunca o exceptie daca nu exista produsul
     */

    public void deleteProduct(int id) throws SQLException {
         productDAO.delete(id);
    }

    /**
     * <p>
     *     Metoda pentru a afisa toate obiectele de tip Product
     * </p>
     * @return o lista de obiecte de tip Product
     * @throws SQLException
     */
    public List<Product> viewAllProducts() throws SQLException {
        return productDAO.showAll();
    }
}