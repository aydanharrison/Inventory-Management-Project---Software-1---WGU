package Controller;

import Model.*;
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

import static Controller.AddPartController.isDecimal;
import static Controller.AddPartController.isNumber;

/**Controller for Modify Part Screen.
 * @author Aydan Harrison*/
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    //radio buttons
    /**Button for inHouse. */
    @FXML
    private RadioButton inHouseModRBtn;
    /**Button for outsource. */
    @FXML
    private RadioButton outsourcedModRBtn;


    //label
    /**Machine id label. */
    public Label machineIDLabel;


    //text fields
    /**Part inventory text field. */
    @FXML
    private TextField invModTxt;
    /**Part machine id or company name text field. */
    @FXML
    private TextField machineIdModTxt;
    /**Part max text field. */
    @FXML
    private TextField maxModTxt;
    /**Part min text field. */
    @FXML
    private TextField minModTxt;
    /**Part name text field. */
    @FXML
    private TextField nameModTxt;
    /**Part id text field. */
    @FXML
    private TextField partIdModtxt;
    /**Part price text field. */
    @FXML
    private TextField priceModTxt;


    //variable for selected part
    /**Variable for part selected from main screen. */
    private Part partSelected;



    /**This saves modified part and adds it to part list.
     * ALso checks if all data was entered correctly.
     * @param event button*/
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            String name = nameModTxt.getText();
            int stock = Integer.parseInt(invModTxt.getText());
            double price = Double.parseDouble(priceModTxt.getText());
            int max = Integer.parseInt(maxModTxt.getText());
            int min = Integer.parseInt(minModTxt.getText());


            if (name.isEmpty() || isNumber(name)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Must declare part name");
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
            else if (!isDecimal(String.valueOf(price)) || price <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be a decimal and greater than 0");
                alert.showAndWait();
                return;
            }


            if (inHouseModRBtn.isSelected()) {
                if (machineIdModTxt.getText().isEmpty() || !isNumber(machineIdModTxt.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please enter valid machine id.");
                    alert.showAndWait();
                    return;
                }
                else {
                    int machineId = Integer.parseInt(machineIdModTxt.getText());
                    Part addedPart =new InHouse(partSelected.getId(), name, price, stock, min, max, machineId);
                    int index = Inventory.getAllParts().indexOf(partSelected);
                    Inventory.updatePart(index, addedPart);

                }
            }
            if (outsourcedModRBtn.isSelected()) {
                if (isNumber(machineIdModTxt.getText()) || machineIdModTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please enter valid company name.");
                    alert.showAndWait();
                    return;
                }
                else {
                    String companyName = machineIdModTxt.getText();
                    Part addedPart =new Outsourced(partSelected.getId(), name, price, stock, min, max, companyName);
                    int index = Inventory.getAllParts().indexOf(partSelected);
                    Inventory.updatePart(index, addedPart);
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



    /**Sets label and part type when InHouse radio button selected. */
    public void onInHouse(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
    }
    /**Sets label and part type when OutSource radio button selected. */
    public void onOutSourced(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name");
    }



    /**Cancels modifying part.
     * Confirms with user if no longer wants to modify part.
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


    /**Sends part from Main Screen to this Modify Part Screen.
     * @param part selected part*/
    public void sendPart(Part part) {
        partSelected = part;
        partIdModtxt.setText(String.valueOf(part.getId()));
        nameModTxt.setText(part.getName());
        invModTxt.setText(String.valueOf(part.getStock()));
        priceModTxt.setText(String.valueOf(part.getPrice()));
        maxModTxt.setText(String.valueOf(part.getMax()));
        minModTxt.setText(String.valueOf(part.getMin()));
        if (part instanceof InHouse){
            inHouseModRBtn.setSelected(true);
            String s;
            s = Integer.toString(((InHouse) part).getMachineId());
            machineIdModTxt.setText(s);
            machineIDLabel.setText("Machine ID");
        }
        else {
            outsourcedModRBtn.setSelected(true);
            String s = ((Outsourced) part).getCompanyName();
            machineIdModTxt.setText(s);
            machineIDLabel.setText("Company Name");
        }
    }

    /**Initializes the modify part page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            
    }

}


