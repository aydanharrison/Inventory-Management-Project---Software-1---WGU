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

import static Controller.MainScreenController.getPartID;
import static Controller.MainScreenController.searchByPartName;
/**Controller for Modify Product Screen.
 *
 * @author Aydan Harrison*/
public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;

    /**List of associated parts*/
    ObservableList<Part> associatedPartsProducts = FXCollections.observableArrayList();

    //top table of parts
    /**Add product table. */
    @FXML
    private TableView<Part> modifyProductPartTableView;
    /**Part ID column. */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**Part name column*/
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**Part inventory column. */
    @FXML
    private TableColumn<Part, Integer> invLevelCol;
    /**Part price column. */
    @FXML
    private TableColumn<Part, Double> priceCol;


    //bottom table/associated parts
    /**Associated parts table. */
    @FXML
    private TableView<Part> modifyProductPartTableViewTwo;
    /**Associated parts part id column. */
    @FXML
    private TableColumn<?, ?> partIdColTwo;
    /**Associated parts part name column. */
    @FXML
    private TableColumn<?, ?> partNameColTwo;
    /**Associated parts inventory column. */
    @FXML
    private TableColumn<?, ?> invColTwo;
    /**Associated parts price column. */
    @FXML
    private TableColumn<?, ?> priceColTwo;


    //text fields
    /**Product min text field. */
    @FXML
    private TextField modifyProductMinTxt;
    /**Product name text field. */
    @FXML
    private TextField modifyProductNameTxt;
    /**Product price text field. */
    @FXML
    private TextField modifyProductPriceTxt;
    /**Product id text field. */
    @FXML
    private TextField modifyProductIdTxt;
    /**Product inventory text field. */
    @FXML
    private TextField modifyProductInvTxt;
    /**Product max text field. */
    @FXML
    private TextField modifyProductMaxTxt;
    /**Part search text field. */
    @FXML
    private TextField modifyProductSearchTxt;



    /**Variable for product being modified. */
    private Product productBeingMod;

    /**Variable for new product*/
    Product addedProduct = new Product(0,"",0.0,0,0,0);

    /**This checks if product name is entered as number.
     * @param prodName name of product*/
    public static boolean isNumber(String prodName){
        if (prodName == null) {
            return false;
        }
        try {
            int x = Integer.parseInt(prodName);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    //search parts
    /**Searches for part by Name or ID.
     * Checks if part exists and filters table.
     *@param  actionEvent search bar
     */
    public void searchPartAddProduct(ActionEvent actionEvent) {
        String s = modifyProductSearchTxt.getText();

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
        modifyProductPartTableView.setItems(parts);
        modifyProductSearchTxt.setText("");
    }


    //cancel modifying product
    /**Cancels modifying product.
     * Confirms with user if no longer wants to modify product.
     * @param event button*/
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values. Do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    //add associated part
    /**Adds part to associated parts list.
     *@param  event search button
     */
    @FXML
    void onActionAdd(ActionEvent event) {
        Part partSelected = modifyProductPartTableView.getSelectionModel().getSelectedItem();
        if (partSelected != null){
            associatedPartsProducts.add(partSelected);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Part");
            alert.showAndWait();
        }
    }

    //remove associated part from ap table
    /**Removes part from associated parts list.
     *@param  event search button
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        if (modifyProductPartTableViewTwo.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deletion of Item is OK?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                associatedPartsProducts.remove(modifyProductPartTableViewTwo.getSelectionModel().getSelectedItem());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select part to remove");
            alert.showAndWait();
        }
    }


    // save modified product
    /**Saves modifications to product. */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            String name = modifyProductNameTxt.getText();
            int stock = Integer.parseInt(modifyProductInvTxt.getText());
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());

            if (name.isEmpty() || isNumber(name)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Must declare part name using letters");
                alert.showAndWait();
                return;
            }
            if (price <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be greater than 0");
                alert.showAndWait();
                return;
            }
            if (min < 0 || min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be greater than 0 and less than Max");
                alert.showAndWait();
                return;
            }
            else if (stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be between min and max");
                alert.showAndWait();
                return;
            }


            Product addedProduct =new Product(productBeingMod.getId(), name, price, stock, min, max);
            for (Part part : associatedPartsProducts) {
                addedProduct.addAssociatedPart(part);
            }

            int index = Inventory.getAllProducts().indexOf(productBeingMod);
            Inventory.updateProduct(index, addedProduct);




            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();



        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter valid text for each field.");
            alert.showAndWait();
        }
    }


    //send product to modify product page from main screen
    /**This method sends selected product from Main screen to modify product page.
     *
     * RUNTIME ERROR: A runtime error that was corrected was the associated parts list of the product
     * being modified was showing up as null. This was fixed by creating a variable for the
     * product being modified (productToModify) and setting it to the product in the
     * send product method. The getAssociatedParts method was then called to get that list
     * and populate the corresponding table.
     *
     * @param product selected product from main screen.*/
    public void sendProduct(Product product) {
        productBeingMod = product;
        modifyProductIdTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        associatedPartsProducts.setAll(productBeingMod.getAllAssociatedParts());
    }


    /**This initializes the modify product page.
     * It also sets the parts table and the associated parts table. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize to part table
        modifyProductPartTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //set associated part table (bottom table) column values
        modifyProductPartTableViewTwo.setItems(associatedPartsProducts);
        partIdColTwo.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColTwo.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColTwo.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
