/**
 * C482 Software 1
 * Din Spataj
 * 001325332
 * 8/13/2020
 * */
package InventorySystem.View_Controller;

import InventorySystem.Model.*;
import InventorySystem.View_Controller.MainScreen.*;
import static InventorySystem.Model.Inventory.*;
import static InventorySystem.View_Controller.MainScreen.getPartModify;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ModifyPart implements Initializable{
    @FXML
    private RadioButton modifyPartInHouse;
    @FXML
    private RadioButton ModifyPartOutsourced;
    @FXML
    private TextField modifypartID;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private TextField modifyPartMachineID;
    @FXML
    private Button modifyPart;
    @FXML
    private Button cancelModify;
    @FXML
    private Text inHouseOrOutsourced;

    private boolean outsourcedBool;
    private Stage stage;
    private Parent scene;
    private int modify;

    /**
     * getting/looking-up all the information of the selected parts*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Part modifying = Inventory.lookupPart(getPartModify());
        modify = Inventory.getAllParts().indexOf(modifying);
        int id = modifying.getId();
        double price = modifying.getPrice();
        int inv = modifying.getStock();
        modifypartID.setText(Integer.toString(id));
        modifyPartName.setText(modifying.getName());
        modifyPartPrice.setText(Double.toString(price));
        modifyPartInv.setText(Integer.toString(inv));
        modifyPartMin.setText(Integer.toString(modifying.getMin()));
        modifyPartMax.setText(Integer.toString(modifying.getMin()));
        if(modifying instanceof OutsourcedPart){
            modifyPartMachineID.setText(((OutsourcedPart) modifying).getCompanyName().getValue().toString());
            modifyPartInHouse.setSelected(false);
            ModifyPartOutsourced.setSelected(true);
            inHouseOrOutsourced.setText("Outsourced");
            outsourcedBool = true;
        }else{
            modifyPartMachineID.setText(((InhousePart) modifying).getMachineId().getValue().toString());
            modifyPartInHouse.setSelected(true);
            ModifyPartOutsourced.setSelected(false);
            inHouseOrOutsourced.setText("machine Id");
            outsourcedBool = false;
        }

    }

    /**
     * Sets the part to inhouse and displays the machine id*/
    @FXML
    private void modifyInHouse(ActionEvent actionEvent) {
        inHouseOrOutsourced.setText("MachineID: ");
        modifyPartMachineID.setPromptText("Enter Machine ID");
        outsourcedBool = false;
        ModifyPartOutsourced.setSelected(false);
    }

    /**
     * Sets the part to outsourced and displays the company name*/
    @FXML
    private void modifyOutsourced(ActionEvent actionEvent) {
        inHouseOrOutsourced.setText("Company Name: ");
        modifyPartMachineID.setPromptText("Enter Company Name");
        outsourcedBool = true;
        modifyPartInHouse.setSelected(false);
    }

    /**
     * This is going to validate inputs, make sure that the user inputs something, makes sure min is less than max,
     * makes sure that inventory/stock is less than max and more than min. It also modifies the part at the end and will redirect ot the mainscreen
     * */
    @FXML
    private void modifyPart(ActionEvent actionEvent) {
        try {
            // Checking if all the input fields are filled
            TextField[] fields = {modifyPartName, modifyPartPrice, modifyPartInv, modifyPartMin, modifyPartMax};
            if (modifyPartInHouse.isSelected() || ModifyPartOutsourced.isSelected()) {

                //check if input fields are filled
                for (TextField input : fields) {
                    if (input.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in all the fields", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //check if number inputs are integers or doubles and not characters
                TextField[] numFields = { modifyPartInv, modifyPartMax, modifyPartMin};
                for (TextField input : numFields) {

                    if (Integer.parseInt(input.getText()) < 0 || Double.parseDouble(input.getText()) < 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in the min, max, inv, and price fields with numeric values only!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //checking min & max & inv/stock
                if (Integer.parseInt(modifyPartMin.getText()) > Integer.parseInt(modifyPartMax.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The min can not be greater than the max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else if ((Integer.parseInt(modifyPartInv.getText()) < Integer.parseInt(modifyPartMin.getText())) || (Integer.parseInt(modifyPartInv.getText()) > Integer.parseInt(modifyPartMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please make sure the inventory/stock field is between min and max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else {
                    IntegerProperty id = new SimpleIntegerProperty(Integer.parseInt(modifypartID.getText()));
                    StringProperty name = new SimpleStringProperty(modifyPartName.getText());
                    IntegerProperty inv = new SimpleIntegerProperty(Integer.parseInt(modifyPartInv.getText()));
                    DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(modifyPartPrice.getText()));
                    IntegerProperty min = new SimpleIntegerProperty(Integer.parseInt(modifyPartMin.getText()));
                    IntegerProperty max = new SimpleIntegerProperty(Integer.parseInt(modifyPartMax.getText()));

                    if (!outsourcedBool) {
                        IntegerProperty machineId = new SimpleIntegerProperty(Integer.parseInt(modifyPartMachineID.getText()));
                        InhousePart part = new InhousePart(id, name, price, inv, min, max, machineId);
                        Inventory.updatePart(modify ,part);
                    } else {
                        StringProperty companyName = new SimpleStringProperty(modifyPartMachineID.getText());
                        OutsourcedPart part = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                        Inventory.updatePart(modify, part);
                    }
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }


            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please choose in-house or Outsourced", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please enter a number in the number fields and make sure all fields are filled" + e.toString(), ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    /**
     * Cancels anything from happening/changing and sends the user back to the mainscreen*/
    @FXML
    private void cancelModify(ActionEvent actionEvent) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES) {
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
