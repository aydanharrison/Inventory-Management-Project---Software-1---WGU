module project.inventorymanagementproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.inventorymanagementproject to javafx.fxml;
    exports project.inventorymanagementproject;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.base;
}