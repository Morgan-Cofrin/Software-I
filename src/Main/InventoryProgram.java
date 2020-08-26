package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryProgram extends Application {

    public static void main (String[] args) {
        launch(args);
    }


    @Override
    public void start (Stage stage) throws Exception {

        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainScreen.fxml"));
        ViewController.MainScreenController controller = new ViewController.MainScreenController(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void addTestData (Inventory inv) {

        //Add InHouse Parts
        Part a1 = new InHouse(1, "Part A1", 2.99, 10, 5, 100, 101);
        Part a2 = new InHouse(3, "Part A2", 4.99, 11, 5, 100, 103);
        Part b = new InHouse(2, "Part B", 3.99, 9, 5, 100, 102);
        inv.addPart(a1);
        inv.addPart(b);
        inv.addPart(a2);
        inv.addPart(new InHouse(4, "Part A3", 5.99, 15, 5, 100, 104));
        inv.addPart(new InHouse(5, "Part A4", 6.99, 5, 5, 100, 105));

        //Add Outsourced Parts
        Part o1 = new Outsourced(6, "Part O1", 2.99, 10, 4, 100, "ACME Co.");
        Part p = new Outsourced(7, "Part P", 3.99, 9, 5, 100, "ACME Co.");
        Part q = new Outsourced(8, "Part Q", 2.99, 10, 5, 100, "Florida Co.");
        inv.addPart(o1);
        inv.addPart(p);
        inv.addPart(q);
        inv.addPart(new Outsourced(9, "Part R", 2.99, 10, 5, 100, "Florida Co."));
        inv.addPart(new Outsourced(10, "Part O2", 2.99, 10, 5, 100, "NY Co."));

        //Add Products
        Product prod1 = new Product(100, "Product 1", 9.99, 20, 5, 100);
        prod1.addAssociatedPart(a1);
        prod1.addAssociatedPart(o1);
        inv.addProduct(prod1);
        Product prod2 = new Product(200, "Product 2", 9.99, 29, 5, 100);
        prod2.addAssociatedPart(a2);
        prod2.addAssociatedPart(p);
        inv.addProduct(prod2);
        Product prod3 = new Product(300, "Product 3", 9.99, 30, 5, 100);
        prod3.addAssociatedPart(b);
        prod3.addAssociatedPart(q);
        inv.addProduct(prod3);
        inv.addProduct(new Product(400, "Product 4", 29.99, 20, 5, 100));
        inv.addProduct(new Product(500, "Product 5", 29.99, 9, 5, 100));

    }
}