package ViewController;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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

public class AddPartController implements Initializable {

    //Buttons,lists,fields, and tables inside
    Inventory inv;

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
    private TextField textFieldPartMax;
    @FXML
    private TextField textFieldPartMin;
    @FXML
    private Label lblCompanyOrMachineID;
    @FXML
    private TextField textFieldCompanyOrMachineID;

    public AddPartController(Inventory inv) {
        this.inv = inv;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblPartIDToChange.setText(setlblPartIDToChange());

        final ToggleGroup group = new ToggleGroup();
        btnRadioInHouse.setToggleGroup(group);
        btnRadioInHouse.setSelected(true);
        btnRadioOutsourced.setToggleGroup(group);

    }


    @FXML
    void InHouseSelected(MouseEvent event) {

        lblCompanyOrMachineID.setText("Machine ID");
        textFieldCompanyOrMachineID.setPromptText("Machine ID");

    }

    @FXML
    void OutSourcedSelected(MouseEvent event) {

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
                    inv.addPart(newInHouse);
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
                inv.addPart(newOutSourced);
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

    public String setlblPartIDToChange() {

    int randomInt = (int) ((Math.random() * 81) + 20);
    return String.valueOf(randomInt);

    }

}
