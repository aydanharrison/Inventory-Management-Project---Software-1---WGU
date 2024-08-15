package project.inventorymanagementproject;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class. 
 *
 * FUTURE ENHANCEMENT: A future modification that could be made
 * to the application is updating the associated parts
 * list when a part is updated. This application does not currently
 * update a part on an associated
 * parts list when that part is updated.
 *
 *
 * The JavaDoc is located:
 * /Users/aydanharrison/IdeaProjects/InventoryManagementProject/src/main/c482javadoc/script-dir/index.html
 *
 * @author Aydan Harrison
 */
public class HelloApplication extends Application {
    /**This opens the application to the main screen. */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/View/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory Management Application");
        stage.setScene(scene);
        stage.show();
    }

    /**This initializes practice data. */
    public static void main(String[] args) {
        Part inhouse1 = new InHouse(Inventory.makePartID(), "handle", 10.00, 3, 3, 5, 10);
        Part inhouse2 = new InHouse(Inventory.makePartID(), "screw", 5.00, 2, 1, 3, 12);
        Part outsource1 = new Outsourced(Inventory.makePartID(), "nail", 6.00, 8, 4, 10, "Apple");



        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(outsource1);

        Product product1 = new Product(Inventory.makeProductID(), "phone", 200.00, 5, 1, 8);
        Product product2 = new Product(Inventory.makeProductID(), "piece", 12.00, 4, 3, 8);
        Product product3 = new Product(Inventory.makeProductID(), "laptop", 40.0, 10, 4, 18);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch(args);
    }
}