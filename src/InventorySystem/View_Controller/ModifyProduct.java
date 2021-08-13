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
import static InventorySystem.View_Controller.MainScreen.errorAlert;
import static InventorySystem.View_Controller.MainScreen.confirmAlert;
import static InventorySystem.View_Controller.MainScreen.getProdModify;

import javafx.beans.Observable;
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

public class ModifyProduct implements Initializable{
    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TextField partSeach;
    @FXML
    private TableColumn <Part, Double> part_cost1;
    @FXML
    private TableColumn <Part, Integer>part_inv1;
    @FXML
    private TableColumn <Part, String>part_name1;
    @FXML
    private TableColumn <Part, Integer>part_id1;
    @FXML
    private TableView<Part> part_table1;
    @FXML
    private TableColumn <Part, Double>part_cost;
    @FXML
    private TableColumn <Part, Integer>part_inv;
    @FXML
    private TableColumn <Part, String>part_name;
    @FXML
    private TableColumn <Part, Integer>part_id;
    @FXML
    private TableView<Part> part_table;
    @FXML
    private Button modifyPart;
    @FXML
    private Button removePart;
    @FXML
    private Button saveModifyProduct;
    @FXML
    private Button cancelModifyProduct;

    private int modify;
    private Stage stage;
    private Parent scene;
    private ObservableList<Part> filledParts = FXCollections.observableArrayList();
    private ObservableList<Part> partsThatNeedSaving = FXCollections.observableArrayList();

    /**
     * getting/looking-up all the information of the selected product
     * and getting the information for the two parts tables.
     *
     * It also sets the parts that are currently associated with this product*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product modifyPro = Inventory.lookupProduct(getProdModify());
        modify = Inventory.getAllProducts().indexOf(modifyPro);

        //Filling Textfields with values
        modifyProductID.setText(modifyPro.getId().getValue().toString());
        modifyProductName.setText(modifyPro.getName().getValue());
        modifyProductInv.setText(modifyPro.getStock().getValue().toString());
        modifyProductPrice.setText(modifyPro.getPrice().getValue().toString());
        modifyProductMin.setText(modifyPro.getMin().getValue().toString());
        modifyProductMax.setText(modifyPro.getMax().getValue().toString());

        partsThatNeedSaving = modifyPro.getAllAssociatedParts();

        //Filling the tables
        part_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        part_name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        part_inv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        part_cost.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        part_id1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        part_name1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        part_inv1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        part_cost1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));

        availablePartsTable();
        part_table1.setItems(partsThatNeedSaving);
    }

    /***
     *Searches for parts so the user can add them to the associated parts table
     */

    @FXML
    private void partSearchHandler(ActionEvent actionEvent) {
        if (checkInt(partSeach.getText())) {
            if(Inventory.lookupPart(Integer.parseInt(partSeach.getText())) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(Integer.parseInt(partSeach.getText())));
                part_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + partSeach.getText() + "\" isn't in the inventory.");
            }
        }else if(partSeach.getText().isEmpty()){
            part_table.setItems(getAllParts());
        }
        else {
            if(Inventory.lookupPart(partSeach.getText()) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(partSeach.getText()));
                part_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + partSeach.getText() + "\" isn't in the inventory.");
            }
        }
    }

    /**
     * Adds a part to the associated parts table*/
    @FXML
    private void modifyPartHandler(ActionEvent actionEvent) {
        Part part = part_table.getSelectionModel().getSelectedItem();
        if(part != null){
            partsThatNeedSaving.add(part);
            addPartsTable();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to add!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /***
     * Removes a associated part
     */

    @FXML
    private void removePartHandler(ActionEvent actionEvent) {
        try{
            Part delete = part_table1.getSelectionModel().getSelectedItem();
            if (confirmAlert("Delete?", "Are you sure you want to remove this part from the list?")) {
                partsThatNeedSaving.remove(delete);
                addPartsTable();
            }

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to remove!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * This is going to validate inputs, make sure that the user inputs something, makes sure min is less than max,
     * makes sure that inventory/stock is less than max and more than min. It also modifies the product at the end and will redirect ot the mainscreen
     * */
    @FXML
    private void savemodifyProductHandler(ActionEvent actionEvent) {

        try {
            // Checking if all the input fields are filled
            TextField[] fields = {modifyProductName, modifyProductPrice, modifyProductInv, modifyProductMax, modifyProductMin};

            //check if input fields are filled
            for (TextField input : fields) {

                if (input.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in all the fields", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }

            //check if number inputs are integers or doubles and not characters
            TextField[] numFields = {modifyProductInv, modifyProductMax, modifyProductMin };
            for (TextField input : numFields) {

                if (Integer.parseInt(input.getText()) < 0 || Double.parseDouble(input.getText()) < 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in the min, max, inv, and price fields with numeric values only!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }

            //checking min & max & inv/stock
            if (Integer.parseInt(modifyProductMin.getText()) > Integer.parseInt(modifyProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The min can not be greater than the max!", ButtonType.OK);
                alert.showAndWait();
                return;
            } else if ((Integer.parseInt(modifyProductInv.getText()) < Integer.parseInt(modifyProductMin.getText())) || (Integer.parseInt(modifyProductInv.getText()) > Integer.parseInt(modifyProductMax.getText()))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please make sure the inventory/stock field is between min and max!", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                IntegerProperty id = new SimpleIntegerProperty(Integer.parseInt(modifyProductID.getText()));
                StringProperty name = new SimpleStringProperty(modifyProductName.getText());
                IntegerProperty inv = new SimpleIntegerProperty(Integer.parseInt(modifyProductInv.getText()));
                DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(modifyProductPrice.getText()));
                IntegerProperty min = new SimpleIntegerProperty(Integer.parseInt(modifyProductMin.getText()));
                IntegerProperty max = new SimpleIntegerProperty(Integer.parseInt(modifyProductMax.getText()));

                Product product = new Product(id, name, price, inv, max, min);
                for (Part part : partsThatNeedSaving) {
                    product.addAssociatedPart(part);
                }
                Inventory.updateProduct(modify, product);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please enter a number in the number fields and make sure all fields are filled", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }

    /**
     * Cancels anything from happening/changing and sends the user back to the mainscreen*/
    @FXML
    private void cancelmodifyProductHandler(ActionEvent actionEvent) throws Exception{
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

    /**
     * This function checks if
     * @param input is a string or a int
     * it returns a boolean*/
    private boolean checkInt(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This updates the associated parts table*/
    private void addPartsTable(){
        part_table1.setItems(partsThatNeedSaving);
    }

    /**
     * This gets all the available parts and displays them in the parts table*/
    private void availablePartsTable(){
        part_table.setItems(getAllParts());
    }
}
