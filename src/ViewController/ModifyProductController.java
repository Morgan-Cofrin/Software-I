package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    //Buttons,lists,fields, and tables inside
    Inventory inv;
    Product selectedProduct;

    @FXML
    private Button btnSearchPart;
    @FXML
    private TextField partSearchBar;
    @FXML
    private Button btnAddAssociatedPart;
    @FXML
    private Button btnDeleteAssociatedPart;
    @FXML
    private Button btnSaveProduct;
    @FXML
    private Button cancelButton;

    @FXML
    private Label lblProductIDToChange;
    @FXML
    private TextField textFieldProductName;
    @FXML
    private TextField textFieldProductInStock;
    @FXML
    private TextField textFieldProductPrice;
    @FXML
    private TextField textFieldProductMin;
    @FXML
    private TextField textFieldProductMax;

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Part> associatedPartsTable;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartInventory = FXCollections.observableArrayList();


    public ModifyProductController(Inventory inv, Product selectedProduct) {
        this.inv = inv;
        this.selectedProduct = selectedProduct;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generatePartsTable();
        generateAssociatedPartsTable();

        lblProductIDToChange.setText(Integer.toString(selectedProduct.getProductID()));
        textFieldProductName.setText(selectedProduct.getProductName());
        textFieldProductInStock.setText(Integer.toString(selectedProduct.getProductInStock()));
        textFieldProductPrice.setText(Double.toString(selectedProduct.getProductPrice()));
        textFieldProductMin.setText(Integer.toString(selectedProduct.getMin()));
        textFieldProductMax.setText(Integer.toString(selectedProduct.getMax()));

    }


    @FXML
    void searchPart(MouseEvent event) {

        String partQuery = partSearchBar.getText();
        partInventorySearch.clear();

        for (Part part : partInventory) {
            if (part.getPartName().contains(partQuery)) {
                partInventorySearch.add(part);
                partsTable.setItems(partInventorySearch);
                partsTable.refresh();
            }
            if (partQuery.equals("")){
                partsTable.setItems(partInventory);
                partsTable.refresh();
            }
        }
    }

    @FXML
    void addAssociatedPart(MouseEvent event) {

        Part associatedPartToAdd = partsTable.getSelectionModel().getSelectedItem();
        associatedPartInventory.add(associatedPartToAdd);
        associatedPartsTable.setItems(associatedPartInventory);
        associatedPartsTable.refresh();

    }

    @FXML
    void deleteAssociatedPart(MouseEvent event) {

        Part associatedPart = associatedPartsTable.getSelectionModel ().getSelectedItem ();

        if ( associatedPart == null ) {
            Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteAlert.setTitle("Information");
            deleteAlert.setHeaderText("Select a part to delete");
            deleteAlert.showAndWait();
        }
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Confirm deletion");
            confirmationAlert.setContentText("Are you sure you want to delete " + associatedPart.getPartName());

            Optional <ButtonType> result = confirmationAlert.showAndWait();
            if ( result.get() == ButtonType.OK ){
                inv.deletePart(associatedPart);
                associatedPartInventory.remove(associatedPart);
                associatedPartsTable.refresh();
            }
        }
    }

    @FXML
    void saveProduct(MouseEvent event) {

        if ( Integer.parseInt(textFieldProductInStock.getText()) >= Integer.parseInt(textFieldProductMin.getText()) &&
             Integer.parseInt(textFieldProductInStock.getText()) <= Integer.parseInt(textFieldProductMax.getText()) &&
             Integer.parseInt(textFieldProductInStock.getText()) >= (0) &&
             Double.parseDouble(textFieldProductPrice.getText()) > (0)
        ) {

            Product newProduct = new Product(
                    Integer.parseInt(lblProductIDToChange.getText()),
                    textFieldProductName.getText(),
                    Double.parseDouble(textFieldProductPrice.getText()),
                    Integer.parseInt(textFieldProductInStock.getText()),
                    Integer.parseInt(textFieldProductMin.getText()),
                    Integer.parseInt(textFieldProductMax.getText())
            );
            for ( int i = 0; i < associatedPartInventory.size(); i++ ) {
                newProduct.addAssociatedPart(associatedPartInventory.get(i));
            }

            inv.updateProduct(newProduct);
            gotoMainScreen(event);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Error in assigning values");
            alert.setContentText("Ensure inventory count is between min and max, and all numbers are positive values.");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(MouseEvent event) {
        gotoMainScreen(event);
    }


    private void generatePartsTable() {
        partInventory.setAll(inv.getAllParts());
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    private void generateAssociatedPartsTable(){
        associatedPartInventory.setAll(selectedProduct.getAssociatedParts());
        associatedPartsTable.setItems(associatedPartInventory);
        associatedPartsTable.refresh();
    }

    public void gotoMainScreen(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/ViewController/C482 GUI.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
            System.out.println(e);
        }
    }

}