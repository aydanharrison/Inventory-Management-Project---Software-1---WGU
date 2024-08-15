package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for Inventory
 *
 * @author Aydan Harrison
 */
public class Inventory {

    /** variable for creating new part id*/
    private static int partId = 0;

    /** variable for creating new product id*/
    private static int productId = 0;

    /**Creates a new part id.
     * @return Creates a unique and new part ID.
     */
    public static int makePartID(){
        return ++partId;
    }

    /**Creates a new product id.
     * @return Creates a unique and new product ID.
     */
    public static int makeProductID(){
        return ++productId;
    }


    /**This is an observable list of all the parts */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**This is an observable list of all the products */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //part methods/etc
    /** This adds a new part to the allParts list
     * @param newPart new part on list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Uses allParts list to look up a part by id
     * @param partId  id of a part to lookup
     * @return partId  parts that match id
     */
    public static Part lookupPart(int partId) {
        return allParts.get(partId);
    }

    /** Update part in allParts list
     * @param index of part
     * @param selectedPart part to update
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /** This removes a part from the allParts list
     * @param selectedPart part to be removed
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /** this gets all the parts on the list
     * @return allParts list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }



    //product methods/etc
    /** This adds a new part to the allParts list
     * @param newProduct new part on list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Uses allProducts list to look up a product by id
     * @param productId  id of a product to lookup
     * @return partId  products that match id
     */
    public static Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    /** Update part in allProducts list
     * @param index of part
     * @param selectedProduct product to update
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** This removes a product from the allProducts list
     * @param selectedProduct product to be removed
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** this gets all the products on the list
     * @return allProducts list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    /**This gets all parts that match search.
     * @return parts that match*/
    public static ObservableList<Part> lookupPart(String partName){
        return (ObservableList<Part>) allParts.get(partId);
    }



    /**This gets all products that match search.
     * @return products that match*/
    public static ObservableList<Product> lookupProduct(String productName){
        return (ObservableList<Product>) allProducts.get(productId);
    }


}
