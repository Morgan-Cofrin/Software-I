package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    //Buttons,lists,fields, and tables inside
    Inventory inv;

    @FXML
    private Button btnSearchPart;
    @FXML
    private Button btnAddPart;
    @FXML
    private Button btnModifyPart;
    @FXML
    private Button btnDeletePart;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnModifyProduct;
    @FXML
    private Button btnDeleteProduct;
    @FXML
    private Button exitButton;

    @FXML
    private TextField partSearchBar;
    @FXML
    private TextField productSearchBar;

    @FXML
    private TableView < Part >    partsTable;
    @FXML
    private TableView < Product > productsTable;

    private ObservableList < Part >    partInventory          = FXCollections.observableArrayList ();
    private ObservableList < Product > productInventory       = FXCollections.observableArrayList ();
    private ObservableList < Part >    partInventorySearch    = FXCollections.observableArrayList ();
    private ObservableList < Product > productInventorySearch = FXCollections.observableArrayList ();

    public MainScreenController ( Inventory inv ) {
        this.inv = inv;
    }


    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        generatePartsTable ();
        generateProductsTable ();
    }


    @FXML
    void searchPart ( MouseEvent event ) {

        String partQuery = partSearchBar.getText ();
        partInventorySearch.clear ();

        for ( Part part : partInventory ) {
            if ( part.getPartName ().contains (partQuery) ) {
                partInventorySearch.add (part);
                partsTable.setItems (partInventorySearch);
                partsTable.refresh ();
            }
            if ( partQuery.equals ("") ) {
                partsTable.setItems (partInventory);
                partsTable.refresh ();
            }
        }
    }

    @FXML
    void searchProduct ( MouseEvent event ) {

        String productQuery = productSearchBar.getText ();
        productInventorySearch.clear ();

        for ( Product product : productInventory ) {
            if ( product.getProductName ().contains (productQuery) ) {
                productInventorySearch.add (product);
                productsTable.setItems (productInventorySearch);
                productsTable.refresh ();
            }
            if ( productQuery.equals ("") ) {
                partsTable.setItems (partInventory);
                partsTable.refresh ();
            }
        }
    }


    @FXML
    void gotoAddPartScreen ( MouseEvent event ) {

        try {
            FXMLLoader loader = new FXMLLoader (getClass ().getResource ("/ViewController/AddPart.fxml"));
            AddPartController controller = new AddPartController (inv);

            loader.setController (controller);
            Parent root = loader.load ();
            Scene scene = new Scene (root);
            scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource ()).getScene ().getWindow ();
            stage.setScene (scene);
            stage.setResizable (false);
            stage.show ();
        } catch (IOException e) {
            System.out.println (e);
        }

    }

    @FXML
    void gotoModifyPartScreen ( MouseEvent event ) {

        try {
            Part selectedPart = partsTable.getSelectionModel ().getSelectedItem ();

            if ( selectedPart == null ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Select a part to modify");
                alert.showAndWait();
            }

            else {
                FXMLLoader loader = new FXMLLoader (getClass ().getResource ("/ViewController/ModifyPart.fxml"));
                ModifyPartController controller = new ModifyPartController (inv, selectedPart);

                loader.setController (controller);
                Parent root = loader.load ();
                Scene scene = new Scene (root);
                scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource ()).getScene ().getWindow ();
                stage.setScene (scene);
                stage.setResizable (false);
                stage.show ();
            }

        } catch (IOException e) {
            System.out.println (e);
        }
    }

    @FXML
    void deletePart ( MouseEvent event ) {

        Part removePart = partsTable.getSelectionModel ().getSelectedItem ();

        if ( removePart == null ) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
            deleteAlert.setTitle("Attention");
            deleteAlert.setHeaderText(null);
            deleteAlert.setContentText("Select a part to delete");
            deleteAlert.showAndWait();
        }
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Confirm deletion");
            confirmationAlert.setContentText("Are you sure you want to delete " + removePart.getPartName());

            Optional <ButtonType> result = confirmationAlert.showAndWait();
            if ( result.get() == ButtonType.OK ){
                inv.deletePart(removePart);
                partInventory.remove(removePart);
                partsTable.refresh();
            }
        }
    }


    @FXML
    void gotoAddProductScreen ( MouseEvent event ) {

        try {
            FXMLLoader loader = new FXMLLoader (getClass ().getResource ("/ViewController/AddProduct.fxml"));
            AddProductController controller = new AddProductController (inv);

            loader.setController (controller);
            Parent root = loader.load ();
            Scene scene = new Scene (root);
            scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource ()).getScene ().getWindow ();
            stage.setScene (scene);
            stage.setResizable (false);
            stage.show ();
        } catch (IOException e) {
            System.out.println (e);
        }
    }

    @FXML
    void gotoModifyProductScreen ( MouseEvent event ) {

        try {
            Product selectedProduct = productsTable.getSelectionModel ().getSelectedItem ();

            if ( selectedProduct == null ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Select a product to modify");
                alert.showAndWait();
            }

            else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, selectedProduct);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {
            System.out.println (e);
        }
    }

    @FXML
    void deleteProduct ( MouseEvent event ) {

        Product removeProduct = productsTable.getSelectionModel ().getSelectedItem ();

        if ( removeProduct == null ) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
            deleteAlert.setTitle("Warning");
            deleteAlert.setHeaderText(null);
            deleteAlert.setContentText("Select a part to delete");
            deleteAlert.showAndWait();
        }
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Confirm deletion");
            confirmationAlert.setContentText("Are you sure you want to delete " + removeProduct.getProductName());

            Optional <ButtonType> result = confirmationAlert.showAndWait();
            if ( result.get() == ButtonType.OK ){
                inv.deleteProduct(removeProduct);
                productInventory.remove(removeProduct);
                productsTable.refresh();
            }
        }
    }

    @FXML
    void exitApplication ( MouseEvent event ) {
        Platform.exit ();
    }


    private void generatePartsTable () {
        partInventory.setAll (inv.getAllParts ());
        partsTable.setItems (partInventory);
        partsTable.refresh ();
    }

    private void generateProductsTable () {
        productInventory.setAll (inv.getAllProducts ());
        productsTable.setItems (productInventory);
        productsTable.refresh ();
    }

    private < T > TableColumn < T, Double > formatPrice () {
        TableColumn < T, Double > costCol = new TableColumn <> ("Price");
        costCol.setCellValueFactory (new PropertyValueFactory <> ("Price"));
        //Format as currency
        costCol.setCellFactory (( TableColumn < T, Double > column ) -> {
            return new TableCell < T, Double > () {
                @Override
                protected void updateItem ( Double item, boolean empty ) {
                    if ( !empty ) {
                        setText ("$" + String.format ("%10.2f", item));
                    }
                }
            };
        });
        return costCol;
    }      //unused

}