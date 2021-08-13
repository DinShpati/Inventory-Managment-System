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

public class AddPart implements Initializable{
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outsourced;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private Button addPart;
    @FXML
    private Button cancelAddPart;
    @FXML
    private TextField machineOrOutsourced;
    @FXML
    private Text machineOrOutsourcedLabel;

    private int idGen;
    private boolean outsourcedBool;
    private Stage stage;
    private Parent scene;

    @Override /**
     Initilizing... Generates random id for the new part */
    public void initialize(URL url, ResourceBundle rb) {
        int size = Inventory.getAllParts().size();
        Random random = new Random();
        idGen = size + random.nextInt(1000);
    }

    @FXML // This will show the machine id option when its clicked - radio button
    private void showMachine(ActionEvent actionEvent) {
        machineOrOutsourcedLabel.setText("MachineID: ");
        machineOrOutsourced.setPromptText("Enter Machine ID");
        outsourcedBool = false;
        outsourced.setSelected(false);
    }

    @FXML// This will show the company name option when its clicked - radio button
    private void showOutsourced(ActionEvent actionEvent) {
        machineOrOutsourcedLabel.setText("Company Name: ");
        machineOrOutsourced.setPromptText("Enter Company Name");
        outsourcedBool = true;
        inHouse.setSelected(false);
    }

    /**
    * This is going to validate inputs, make sure that the user inputs something, makes sure min is less than max,
    * makes sure that inventory/stock is less than max and more than min. It also adds the part at the end and will redirect ot the mainscreen
    */
    @FXML
    private void addPart(ActionEvent actionEvent) throws IOException{

        try {
            // Checking if all the input fields are filled
            TextField[] fields = {addPartName, addPartPrice, addPartInv, addPartMax, addPartMin};
            if (inHouse.isSelected() || outsourced.isSelected()) {

                //check if input fields are filled
                for (TextField input : fields) {

                    if (input.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in all the fields", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //check if number inputs are integers or doubles and not characters
                TextField[] numFields = {addPartPrice, addPartInv, addPartMax, addPartMin};
                for (TextField input : numFields) {

                    if (Integer.parseInt(input.getText()) < 0 || Double.parseDouble(input.getText()) < 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in the min, max, inv, and price fields with numeric values only!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //checking min & max & inv/stock
                if (Integer.parseInt(addPartMin.getText()) > Integer.parseInt(addPartMax.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The min can not be greater than the max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else if ((Integer.parseInt(addPartInv.getText()) < Integer.parseInt(addPartMin.getText())) || (Integer.parseInt(addPartInv.getText()) > Integer.parseInt(addPartMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please make sure the inventory/stock field is between min and max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else {
                    IntegerProperty id = new SimpleIntegerProperty(this.idGen);
                    StringProperty name = new SimpleStringProperty(addPartName.getText());
                    IntegerProperty inv = new SimpleIntegerProperty(Integer.parseInt(addPartInv.getText()));
                    DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(addPartPrice.getText()));
                    IntegerProperty min = new SimpleIntegerProperty(Integer.parseInt(addPartMin.getText()));
                    IntegerProperty max = new SimpleIntegerProperty(Integer.parseInt(addPartMax.getText()));

                    if (!outsourcedBool) {
                        IntegerProperty machineId = new SimpleIntegerProperty(Integer.parseInt(machineOrOutsourced.getText()));
                        InhousePart part = new InhousePart(id, name, price, inv, min, max, machineId);
                        Inventory.addPart(part);
                    } else {
                        StringProperty companyName = new SimpleStringProperty(machineOrOutsourced.getText());
                        OutsourcedPart part = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                        Inventory.addPart(part);
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please enter a number in the number fields and make sure all fields are filled", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }
    /**
     * cancels the proccess of adding a part
     * */
    @FXML
    private void cancelAddPart(ActionEvent actionEvent) throws IOException{
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
