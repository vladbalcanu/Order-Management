package model;

/**
 * <p>
 *     Aceasta clasa descrie obiectul de tip Orders
 *     Contine doar Constructorul clasei ,gettere si settere
 * </p>
 * @author Vlad-Andrei Balcanu
 */
public class Orders {
    private int id;
    private int idClient;
    private int idProduct;
    private int nrOfProducts;
    private int totalCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getNrOfProducts() {
        return nrOfProducts;
    }

    public void setNrOfProducts(int nrOfProducts) {
        this.nrOfProducts = nrOfProducts;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * <p>
     *     Constructorul clasei
     * </p>
     */
    public Orders(){

    }

    public Orders(int id, int idClient, int idProduct, int nrOfProducts, int totalCost) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.nrOfProducts = nrOfProducts;
        this.totalCost = totalCost;
    }

    public Orders(int idClient, int idProduct, int nrOfProducts) {
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.nrOfProducts = nrOfProducts;
    }
    @Override
    public String toString() {
        return "Order [id=" +
                "" + id +", idClient" +idClient + ", idProduct=" + idProduct +", nrOfProducts="+nrOfProducts+", totalCost="+totalCost + "]";
    }
}
