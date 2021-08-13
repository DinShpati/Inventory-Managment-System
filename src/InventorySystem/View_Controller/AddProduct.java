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

public class AddProduct implements Initializable{
    @FXML
    private Button cancelAddProduct;
    @FXML
    private Button saveAddProduct;
    @FXML
    private Button removePart;
    @FXML
    private Button addPart;
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
    private TextField partSeach;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductID;

    private int idGen;
    private Stage stage;
    private Parent scene;
    private ObservableList<Part> productParts = FXCollections.observableArrayList();


    @Override /**
             * Initilizing... Generates random id for the new product and gets the values for the two tables
             */
    public void initialize(URL url, ResourceBundle rb) {
        int size = Inventory.getAllParts().size();
        Random random = new Random();
        idGen = size + random.nextInt(1000);
        part_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        part_name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        part_inv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        part_cost.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        part_id1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        part_name1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        part_inv1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        part_cost1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        availablePartsTable();
    }

    @FXML /**
            Searches for parts
            */
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

    @FXML // Adds the part from table 1 to table 2
    private void addPartHandler(ActionEvent actionEvent) {

        Part part = part_table.getSelectionModel().getSelectedItem();
        if(part != null){
            productParts.add(part);
            addPartsTable();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to add!", ButtonType.OK);
            alert.showAndWait();
        }

    }
    /**
     * Removes a part from the Products part list
     * */
    @FXML
    private void removePartHandler(ActionEvent actionEvent) {

        try{
            Part delete = part_table1.getSelectionModel().getSelectedItem();
            if (confirmAlert("Delete?", "Are you sure you want to remove this part from the list?")) {
                productParts.remove(delete);
            }

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to remove!", ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * This is going to validate inputs, make sure that the user inputs something, makes sure min is less than max,
     * makes sure that inventory/stock is less than max and more than min. It also adds the product at the end and will redirect ot the mainscreen
    */
    @FXML
    private void saveAddProductHandler(ActionEvent actionEvent) {
        try {
            // Checking if all the input fields are filled
            TextField[] fields = {addProductName, addProductPrice, addProductInv, addProductMax, addProductMin};

                //check if input fields are filled
                for (TextField input : fields) {

                    if (input.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in all the fields", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //check if number inputs are integers or doubles and not characters
                TextField[] numFields = {addProductPrice, addProductInv, addProductMax, addProductMin};
                for (TextField input : numFields) {

                    if (Integer.parseInt(input.getText()) < 0 || Double.parseDouble(input.getText()) < 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please fill in the min, max, inv, and price fields with numeric values only!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }

                //checking min & max & inv/stock
                if (Integer.parseInt(addProductMin.getText()) > Integer.parseInt(addProductMax.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The min can not be greater than the max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else if ((Integer.parseInt(addProductInv.getText()) < Integer.parseInt(addProductMin.getText())) || (Integer.parseInt(addProductInv.getText()) > Integer.parseInt(addProductMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please make sure the inventory/stock field is between min and max!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                } else {
                    IntegerProperty id = new SimpleIntegerProperty(this.idGen);
                    StringProperty name = new SimpleStringProperty(addProductName.getText());
                    IntegerProperty inv = new SimpleIntegerProperty(Integer.parseInt(addProductInv.getText()));
                    DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(addProductPrice.getText()));
                    IntegerProperty min = new SimpleIntegerProperty(Integer.parseInt(addProductMin.getText()));
                    IntegerProperty max = new SimpleIntegerProperty(Integer.parseInt(addProductMax.getText()));

                    Product product = new Product(id, name, price, inv, min, max);
                    for (Part part : productParts) {
                        product.addAssociatedPart(part);
                    }
                    Inventory.addProduct(product);

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
     *  This cancels everything and returns the user to the mainscreen */
    @FXML
    private void cancelAddProductHandler(ActionEvent actionEvent) throws Exception{
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
     * This checks to make sure that the number given is a integer */
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
     * this updates the selected table */
    private void addPartsTable(){
        part_table1.setItems(productParts);
    }

    /**
     * This fills in the first table with all the available parts */
    private void availablePartsTable(){
        part_table.setItems(getAllParts());
    }
}
