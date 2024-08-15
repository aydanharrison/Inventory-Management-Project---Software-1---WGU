package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controller.ModifyProductController.isNumber;

/**Controller for Add Part Screen.
 * @author Aydan Harrison*/
public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;

    //radio buttons
    @FXML
    private ToggleGroup inOutTG;
    /**Button for inHouse*/
    @FXML
    private RadioButton inHouseAddRBtn;
    /**Button for outsource*/
    @FXML
    private RadioButton outsourcedAddRBtn;


    //label that changes w/ toggle group
    /**Machine id label*/
    public Label machineIDLabel;


    //text fields
    /**Inventory text field*/
    @FXML
    private TextField invAddTxt;
    /**Machine ID / Company Name text field*/
    @FXML
    private TextField machineIdComNameAddTxt;
    /**Max text field*/
    @FXML
    private TextField maxAddTxt;
    /**Minimum text field*/
    @FXML
    private TextField minAddTxt;
    /**Part name text field*/
    @FXML
    private TextField nameAddTxt;
    /**Part ID text field*/
    @FXML
    private TextField partIdAddTxt;
    /**Price text field*/
    @FXML
    private TextField priceAddTxt;

    /**String for in house vs outsourced part. */
    @FXML
    private String partInOut;


    //checks for company name/machine id is number
    /**This checks if Machine id/company name is entered as number.
     * @param mIDcName mahcine id or company name*/
    public static boolean isNumber(String mIDcName){
        if (mIDcName == null) {
            return false;
        }
        try {
            int x = Integer.parseInt(mIDcName);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    //checks if price is decimal
    /**This checks if the price is a double. */
    public static boolean isDecimal(String partPrice){
        if (partPrice == null) {
            return false;
        }
        try {
            double decimal = Double.parseDouble(partPrice);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    //save new part
    /**This saves new part and adds it to part list.
     * ALso checks if all data was entered correctly.
     * @param event button*/
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            String name = nameAddTxt.getText();
            int stock = Integer.parseInt(invAddTxt.getText());
            String priceString = priceAddTxt.getText();
            //double price = Double.parseDouble(priceAddTxt.getText());
            int max = Integer.parseInt(maxAddTxt.getText());
            int min = Integer.parseInt(minAddTxt.getText());

            if (name.isEmpty() || isNumber(name)){
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
            else if (!isDecimal(String.valueOf(priceString)) || priceString.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be a decimal and greater than 0");
                alert.showAndWait();
                return;
            }

           // double price = Double.parseDouble(priceAddTxt.getText());

            if (inHouseAddRBtn.isSelected()) {
                if (machineIdComNameAddTxt.getText().isEmpty() || !isNumber(machineIdComNameAddTxt.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please enter valid machine id.");
                    alert.showAndWait();
                    return;
                }
                else {
                    double price = Double.parseDouble(priceAddTxt.getText());
                    int machineId = Integer.parseInt(machineIdComNameAddTxt.getText());
                    Inventory.addPart(new InHouse(Inventory.makePartID(), name, price, stock, min, max, machineId));
                }
            }
            if (outsourcedAddRBtn.isSelected()) {
                if (isNumber(machineIdComNameAddTxt.getText()) || machineIdComNameAddTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please enter valid company name.");
                    alert.showAndWait();
                    return;
                }
                else {
                    double price = Double.parseDouble(priceAddTxt.getText());
                    String companyName = machineIdComNameAddTxt.getText();
                    Inventory.addPart(new Outsourced(Inventory.makePartID(), name, price, stock, min, max, companyName));
                }
            }



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


    //buttons for inhouse/outsource part
    /**Sets label and part type when InHouse radio button selected. */
    public void onInHouse(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
        partInOut = "Inhouse";
    }

    /**Sets label and part type when OutSource radio button selected. */
    public void onOutSourced(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name");
        partInOut = "outsource";
    }


    //cancel add new part
    /**Cancels adding new part.
     * Confirms with user if no longer wants to add part
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


    /**This initializes the Add Part page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
