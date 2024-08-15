package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


 /**Controller for the Main screen.
  *
  * A future modification that could be made to the application is updating the associated parts
  * list when a part is updated. This application does not currently update a part on an associated
  * parts list when that part is updated.
 *
 * @author Aydan Harrison
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    //all variables for tables
    /**Table View for Parts list*/
    @FXML
    private TableView<Part> mainPartTV;
    /**refers to Part id column*/
    @FXML
    private TableColumn<Part, Integer> partIdMainCol;
    /**refers to Part name column*/
    @FXML
    private TableColumn<Part, String> partNameMainCol;
    /**refers to Part inventory column*/
    @FXML
    private TableColumn<Part, Integer> invLevelPartMain;
     /**refers to Part price column*/
    @FXML
    private TableColumn<Part, Double> partPriceColMain;


     /**Table View for Products list*/
    @FXML
    private TableView<Product> mainProductTV;
    /**refers to Product id column*/
    @FXML
    private TableColumn<Product, Integer> productIdMainCol;
     /**refers to Product name column*/
    @FXML
    private TableColumn<Product, String> productNameMainCol;
     /**refers to Product inventory column*/
    @FXML
    private TableColumn<Product, Integer> invMainCol;
     /**refers to Product price column*/
    @FXML
    private TableColumn<Product, Double> productPriceMainCol;

    /**Field for Part search*/
    @FXML
    private TextField partSearchMain;
     /**Field for product search*/
    @FXML
    private TextField productSearchMain;



     /**This button brings user to the Add Parts page.
      * @param event button
      */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



     /**This button brings user to the Add Products page.
      * @param event button
      */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    //Deletes for main screen tables
     /**This deletes selected part in parts table.
      * @param event button
      */
    @FXML
    void onActionDeletePart(ActionEvent event) {
      Part p = (Part)mainPartTV.getSelectionModel().getSelectedItem();
       if (p == null){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Please Select Part to Delete");
           alert.showAndWait();
       }
       else if (p != null){
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setContentText("Deletion of Item is OK?");
           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == ButtonType.OK){
               Inventory.deletePart(p);
           }
       }
    }


     /**This deletes selected product in product table.
      * @param event button
      */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product p = (Product) mainProductTV.getSelectionModel().getSelectedItem();
        if (p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Product to Delete");
            alert.showAndWait();
        }
        else if (p.getAllAssociatedParts().size() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot delete product with associated parts");
            alert.showAndWait();
        }
        else if (p != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deletion of Item is OK?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.deleteProduct(p);
            }
        }
    }



    //Modify Buttons
     /**This button brings user to modify part page after selecting part to modify.
      * @param event button
      */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        if (mainPartTV.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Part");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainPartTV.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

     /**This button brings user to modify product page after selecting product to modify.
      * @param event button
      */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        if (mainProductTV.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Product");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPrController = loader.getController();
            MPrController.sendProduct(mainProductTV.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            //scene = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }




    //Product Search
     /**Searches for product by Name or ID.
      * Checks if products exists and filters table.
      *@param  actionEvent search button
      */
    public void searchProduct(ActionEvent actionEvent) {
        String d = productSearchMain.getText();

        ObservableList<Product> Products = searchByProductName(d);

        if (Products.size() == 0) {
            try {
                int id = Integer.parseInt(d);
                Product sp = getProductID(id);
                if (sp != null) {
                    Products.add(sp);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product not found");
                alert.showAndWait();
            }
        }
        mainProductTV.setItems(Products);
        productSearchMain.setText("");
    }

    /**Searches for Product by name and adds to list.
     * Checks for partial name and makes list for products that match search
     * @param partialName any string of letters of search*/
    private ObservableList<Product> searchByProductName(String partialName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product spr : allProducts){
            if (spr.getName().contains(partialName)){
                namedProducts.add(spr);
            }
        }

        return namedProducts;
    }
     /**Searches for Product by id and adds to list.
      * Checks for id and adds to list for products that match search
      * @param id id of product searched*/
    private Product getProductID(int id){
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++){
            Product spr = allProducts.get(i);
            if (spr.getId() == id){
                return spr;
            }
        }

        return null;
    }



     //Part Search
     /**Searches for part by Name or ID.
      * Checks if part exists and filters table.
      *@param  actionEvent search button
      */
    @FXML
    public void searchPart(ActionEvent actionEvent) {
        String s = partSearchMain.getText();

        ObservableList<Part> parts = searchByPartName(s);

        if (parts.size() == 0){
            try {
                int id = Integer.parseInt(s);
                Part sp = getPartID(id);
                if (sp != null) {
                    parts.add(sp);
                }
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }
        mainPartTV.setItems(parts);
        partSearchMain.setText("");
    }

     /**Searches for Product by name and adds to list.
      * Checks for partial name and makes list for products that match search
      * @param partialName any string of letters of search*/
     public static ObservableList<Part> searchByPartName(String partialName){
         ObservableList<Part> namedParts = FXCollections.observableArrayList();

         ObservableList<Part> allParts = Inventory.getAllParts();

         for(Part sp : allParts){
             if (sp.getName().contains(partialName)){
                 namedParts.add(sp);
             }
         }

         return namedParts;
     }

     /**Searches for Part by id and adds to list.
      * Checks for id and adds to list for parts that match search
      * @param id id of product searched*/
    public static Part getPartID(int id){
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++){
            Part sp = allParts.get(i);
            if (sp.getId() == id){
                return sp;
            }
        }

        return null;
    }




    //Exit from main screen
     /**This exits the program.
      * @param event exit button
      */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }



    /**This initializes the Main Screen page.
     * It also sets the two tables, one with all the parts and
     * one with all the products. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPartTV.setItems(Inventory.getAllParts());
        partIdMainCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameMainCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelPartMain.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColMain.setCellValueFactory(new PropertyValueFactory<>("price"));


        mainProductTV.setItems(Inventory.getAllProducts());
        productIdMainCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameMainCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invMainCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceMainCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }



}