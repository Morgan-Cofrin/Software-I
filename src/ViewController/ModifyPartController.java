package ViewController;     //TODO savePart

import Model.*;
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
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    //Buttons,lists,fields, and tables inside
    Inventory inv;
    Part selectedPart;

    @FXML
    private RadioButton btnRadioInHouse;
    @FXML
    private RadioButton btnRadioOutsourced;
    @FXML
    private Button btnSavePart;
    @FXML
    private Button cancelButton;

    @FXML
    private Label lblPartIDToChange;
    @FXML
    private TextField textFieldPartName;
    @FXML
    private TextField textFieldPartInStock;
    @FXML
    private TextField textFieldPartPrice;
    @FXML
    private TextField textFieldPartMin;
    @FXML
    private TextField textFieldPartMax;
    @FXML
    private Label lblCompanyOrMachineID;
    @FXML
    private TextField textFieldCompanyOrMachineID;



    public ModifyPartController(Inventory inv, Part selectedPart) {
        this.inv = inv;
        this.selectedPart = selectedPart;

}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final ToggleGroup group = new ToggleGroup();
        btnRadioInHouse.setToggleGroup(group);
        btnRadioOutsourced.setToggleGroup(group);

        lblPartIDToChange.setText(Integer.toString(selectedPart.getPartID()));
        textFieldPartName.setText(selectedPart.getPartName());
        textFieldPartInStock.setText(Integer.toString(selectedPart.getPartInStock()));
        textFieldPartPrice.setText(Double.toString(selectedPart.getPartPrice()));
        textFieldPartMin.setText(Integer.toString(selectedPart.getMin()));
        textFieldPartMax.setText(Integer.toString(selectedPart.getMax()));

        if (selectedPart instanceof InHouse ) {

            btnRadioInHouse.setSelected(true);
            lblCompanyOrMachineID.setText("Machine ID");
            textFieldCompanyOrMachineID.setPromptText("Machine ID");
            textFieldCompanyOrMachineID.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));

        }

        else if (selectedPart instanceof Outsourced ) {

            btnRadioOutsourced.setSelected(true);
            lblCompanyOrMachineID.setText("Company");
            textFieldCompanyOrMachineID.setPromptText("Company");
            textFieldCompanyOrMachineID.setText(((Outsourced) selectedPart).getCompanyName());

        }

    }


    @FXML
    void InHouseSelected(MouseEvent event) {

        lblCompanyOrMachineID.setText("Machine ID");
        textFieldCompanyOrMachineID.setPromptText("Machine ID");
    }

    @FXML
    void OutsourcedSelected(MouseEvent event) {

        lblCompanyOrMachineID.setText("Company Name");
        textFieldCompanyOrMachineID.setPromptText("Company Name");
    }

    @FXML
    void savePart(MouseEvent event) {

        if ( Integer.parseInt(textFieldPartInStock.getText()) >= Integer.parseInt(textFieldPartMin.getText()) &&
             Integer.parseInt(textFieldPartInStock.getText()) <= Integer.parseInt(textFieldPartMax.getText()) &&
             Integer.parseInt(textFieldPartInStock.getText()) >= (0) &&
             Double.parseDouble(textFieldPartPrice.getText()) > (0)
        ) {

            if (btnRadioInHouse.isSelected()){//machine ID has numbers in it
                if ( !(textFieldCompanyOrMachineID.getText().matches("[0-9]+")) ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Machine ID must contain only numerical values.");
                    alert.showAndWait();
                }
                else {
                    InHouse newInHouse = new InHouse(
                            Integer.parseInt(lblPartIDToChange.getText()),
                            textFieldPartName.getText(),
                            Double.parseDouble(textFieldPartPrice.getText()),
                            Integer.parseInt(textFieldPartInStock.getText()),
                            Integer.parseInt(textFieldPartMin.getText()),
                            Integer.parseInt(textFieldPartMax.getText()),
                            Integer.parseInt(textFieldCompanyOrMachineID.getText())
                    );
                    inv.updatePart(newInHouse);
                    gotoMainScreen(event);
                }
            }

            if (btnRadioOutsourced.isSelected()){
                Outsourced newOutSourced = new Outsourced(
                        Integer.parseInt(lblPartIDToChange.getText()),
                        textFieldPartName.getText(),
                        Double.parseDouble(textFieldPartPrice.getText()),
                        Integer.parseInt(textFieldPartInStock.getText()),
                        Integer.parseInt(textFieldPartMin.getText()),
                        Integer.parseInt(textFieldPartMax.getText()),
                        textFieldCompanyOrMachineID.getText()
                );
                inv.updatePart(newOutSourced);
                gotoMainScreen(event);
            }
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