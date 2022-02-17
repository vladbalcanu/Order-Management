package model;

/**
 * <p>
 *     Aceasta clasa descrie obiecutl de tip Product
 *     Contine Constructorul clasei , gettere si settere
 * </p>
 * @author Vlad-Andrei Balcanu
 */
public class Product {
    private int id;
    private int value;
    private String name;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Constructorul clasei
     */
    public Product(){

    }
    public Product(int id, int value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }

    public Product(int value, String name,int stock) {
        this.value = value;
        this.name = name;
        this.stock=stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Product [id=" + id +", value=" +value + ", name=" + name + "]";
    }
}
