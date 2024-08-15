package Controller;

import Model.*;
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
import static Controller.ModifyProductController.isNumber;

/**Controller for Add Product Screen.
 * @author Aydan Harrison*/
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;

    //list for associated parts table
    /**List of associated parts*/
    public static ObservableList<Part> associatedPartsProducts = FXCollections.observableArrayList();


    //top part table
    /**Add part table. */
    @FXML
    private TableView<Part> addProductTableView;
    /**Part ID column. */
    @FXML
    private TableColumn<Part, Integer> addProductPartIdCol;
    /**Part name column*/
    @FXML
    private TableColumn<Part, String> addProductPartNameCol;
    /**Part price column. */
    @FXML
    private TableColumn<Part, Double> addProductPriceCol;
    /**Part inventory column. */
    @FXML
    private TableColumn<Part, Integer> addProductInvCol;


    //bottom associated parts table
    /**Associated parts table. */
    @FXML
    private TableView<Part> addProductTableViewTwo;
    /**Associated parts inventory column. */
    @FXML
    private TableColumn<Product, Integer> invCol;
    /**Associated parts part id column. */
    @FXML
    private TableColumn<Product, Integer> partIdCol;
    /**Associated parts part name column. */
    @FXML
    private TableColumn<Product, String> partNameCol;
    /**Associated parts price column. */
    @FXML
    private TableColumn<Product, Double> priceCol;


    //text fields
    /**Product id text field. */
    @FXML
    private TextField addProductIdTxt;
    /**Product inventory text field. */
    @FXML
    private TextField addProductInvTxt;
    /**Product max text field. */
    @FXML
    private TextField addProductMaxTxt;
    /**Product min text field. */
    @FXML
    private TextField addProductMinTxt;
    /**Product name text field. */
    @FXML
    private TextField addProductNameTxt;
    /**Product price text field. */
    @FXML
    private TextField addProductPriceTxt;

    /**Part search text field. */
    @FXML
    private TextField addProductSearchTxt;


    //new product being added
    /**Creates variable for new product. */
    Product addedProduct = new Product(0,"",0.0,0,0,0);


    //Part search to add to product
    /**Searches for part by Name or ID.
     * Checks if part exists and filters table.
     *@param  actionEvent search bar
     */
    public void searchPartAddProduct(ActionEvent actionEvent) {
        String s = addProductSearchTxt.getText();

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
        addProductTableView.setItems(parts);
        addProductSearchTxt.setText("");
    }


    //add associated part to bottom table
    /**Adds part to associated parts list.
     *@param  event search button
     */
    @FXML
    void onActionAdd(ActionEvent event) {
        if (addProductTableView.getSelectionModel().getSelectedItem() != null){
            Part partSelected = addProductTableView.getSelectionModel().getSelectedItem();
            addedProduct.addAssociatedPart(partSelected);
            associatedPartsProducts.add(addProductTableView.getSelectionModel().getSelectedItem());
            addProductTableViewTwo.setItems(addedProduct.getAllAssociatedParts());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Part");
            alert.showAndWait();
        }
    }


    //remove associated parts from products
    /**Removes part from associated parts list.
     *@param  event search button
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part partSelected = addProductTableView.getSelectionModel().getSelectedItem();
        if (addProductTableViewTwo.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deletion of Item is OK?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                associatedPartsProducts.remove(addProductTableViewTwo.getSelectionModel().getSelectedItem());
                addedProduct.deleteAssociatedPart(partSelected);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select part to remove");
            alert.showAndWait();
        }
    }


    //add new product
    /**This saves new product and adds it to products list.
     * ALso checks if all data was entered correctly.
     * @param event button*/
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            //int id = Integer.parseInt(partIdAddTxt.getText());
            String name = addProductNameTxt.getText();
            int stock = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

            if (name.isEmpty() || isNumber(name)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Must declare part name using letters");
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
            else if (price <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be greater than 0");
                alert.showAndWait();
                return;
            }

            Product addedProduct =new Product(Inventory.makeProductID(), name, price, stock, min, max);
            for (Part part : associatedPartsProducts) {
                addedProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(addedProduct);


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


    //cancel out of add product page
    /**Cancels adding new product.
     * Confirms with user if no longer wants to add product.
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


    /**This initializes the Add Product page.
     * It also sets the two tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize to part table
        addProductTableView.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //set associated part table (bottom table) column values
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


}
