package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class for products
 * @author Aydan Harrison
 */
public class Product {

    /**This is an observable list of all the parts */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //declare fields
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    //constructor
    /** Constructor for Product
     * @param id - product id
     * @param name - product name
     * @param price - product price
     * @param stock - amount of stock of product
     * @param min - minimum amount of a product
     * @param max - maximum amount of a product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //methods
    /**This gets product id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**This sets product id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**This gets product name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**This sets product name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**This gets product price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**This sets product price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**This gets product stock.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**This sets product stock.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**This gets product minimum.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**This gets product minimum.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**This gets product max.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**This sets product max.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**This adds associated parts to product.
     * @param part part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**This deletes associated parts.
     * @param selectedAssociatedPart part to delete
     * @return true if part was in list and removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**This gets associated parts of product. 
     * @return associatedParts of the product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return  associatedParts;
    }



}