/**
 * C482 Software 1
 * Din Spataj
 * 001325332
 * 8/13/2020
 * */
package InventorySystem.View_Controller;

import InventorySystem.Model.*;
import static InventorySystem.Model.Inventory.*;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Din
 */
public class MainScreen implements Initializable {


    @FXML
    private TextField search;
    @FXML
    private TableView<Part> part_table;
    @FXML
    private TableColumn<Part, Integer> part_id;
    @FXML
    private TableColumn<Part, String> part_name;
    @FXML
    private TableColumn<Part, Integer> part_inv;
    @FXML
    private TableColumn<Part, String> part_cost; // Double
    @FXML
    private TextField pro_search;
    @FXML
    private TableView<Product> pro_table;
    @FXML
    private TableColumn<Product, Integer> pro_id;
    @FXML
    private TableColumn<Product, String> pro_name;
    @FXML
    private TableColumn<Product, Integer> pro_inv;
    @FXML
    private TableColumn<Product, String> pro_cost; // Double
    @FXML
    private Button exit;

    private static int partModifyID = 0;
    private static int prodModifyID = 0;

    private Stage stage;
    private Parent scene;

    //Pre-entered Data
    private static boolean entered;


    /**
     * Adds sample data
     * it also sets the table data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!entered){
            Inventory.addPart(new InhousePart(new SimpleIntegerProperty(4), new SimpleStringProperty("stuff"),
                    new SimpleDoubleProperty(4.99), new SimpleIntegerProperty(5), new SimpleIntegerProperty(1),
                    new SimpleIntegerProperty(7), new SimpleIntegerProperty(456)));
            Inventory.addPart(new InhousePart(new SimpleIntegerProperty(7), new SimpleStringProperty("hjk"),
                    new SimpleDoubleProperty(4.99), new SimpleIntegerProperty(5), new SimpleIntegerProperty(1),
                    new SimpleIntegerProperty(7), new SimpleIntegerProperty(456)));
            Inventory.addPart(new InhousePart(new SimpleIntegerProperty(2), new SimpleStringProperty("John"),
                    new SimpleDoubleProperty(4.99), new SimpleIntegerProperty(5), new SimpleIntegerProperty(1),
                    new SimpleIntegerProperty(7), new SimpleIntegerProperty(456)));
            Inventory.addPart(new InhousePart(new SimpleIntegerProperty(1), new SimpleStringProperty("Dod"),
                    new SimpleDoubleProperty(4.99), new SimpleIntegerProperty(5), new SimpleIntegerProperty(1),
                    new SimpleIntegerProperty(7), new SimpleIntegerProperty(456)));
            Inventory.addProduct(new Product(new SimpleIntegerProperty(1), new SimpleStringProperty("john"), new SimpleDoubleProperty(6.55)
            , new SimpleIntegerProperty(5), new SimpleIntegerProperty(6), new SimpleIntegerProperty(1)));
            Inventory.addProduct(new Product(new SimpleIntegerProperty(5), new SimpleStringProperty("fhg"), new SimpleDoubleProperty(655)
            , new SimpleIntegerProperty(57), new SimpleIntegerProperty(67), new SimpleIntegerProperty(6)));
            Inventory.addProduct(new Product(new SimpleIntegerProperty(456), new SimpleStringProperty("rytrt"), new SimpleDoubleProperty(5)
            , new SimpleIntegerProperty(56), new SimpleIntegerProperty(66), new SimpleIntegerProperty(6)));

            entered = true;
        }
        part_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        part_name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        part_inv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        part_cost.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()).asString());
        pro_id.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        pro_name.setCellValueFactory(cellData -> cellData.getValue().getName());
        pro_inv.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        pro_cost.setCellValueFactory(cellData -> cellData.getValue().getPrice().asString());
        partTableFill();
        proTableFill();
    }    

    /**
     * this searches the parts list
     * */

    @FXML
    private void searchHandler(ActionEvent event) {
        if (checkInt(search.getText())) {
            if(Inventory.lookupPart(Integer.parseInt(search.getText())) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(Integer.parseInt(search.getText())));
                part_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + search.getText() + "\" isn't in the inventory.");
            }
        }else if(search.getText().isEmpty()){
            part_table.setItems(getAllParts());
        }
        else {
            if(Inventory.lookupPart(search.getText()) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(search.getText()));
                part_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + search.getText() + "\" isn't in the inventory.");
            }
        }
    }

    /**
     * this searches the products list
     * */
    @FXML
    private void searchHandlerPro(ActionEvent actionEvent) {
        if (checkInt(pro_search.getText())) {
            if(Inventory.lookupPart(Integer.parseInt(pro_search.getText())) != null) {
                ObservableList<Product> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupProduct(Integer.parseInt(pro_search.getText())));
                pro_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + pro_search.getText() + "\" isn't in the inventory.");
            }
        }else if(pro_search.getText().isEmpty()){
            pro_table.setItems(getAllProducts());
        }
        else {
            if(Inventory.lookupPart(pro_search.getText()) != null) {
                ObservableList<Product> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupProduct(pro_search.getText()));
                pro_table.setItems(tempList);
            }
            else {
                errorAlert("Search Error", "Keyword not found", "The search term \"" + pro_search.getText() +"\" isn't in the inventory.");
            }
        }
    }

    /**
     * @params input is a string or a integer
     * */
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
     * Sends the user to the modify parts screen
     * */
    @FXML
    private void modifyHandler(ActionEvent event) {
        try {
            partModifyID = part_table.getSelectionModel().getSelectedItem().getId();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            stage.setTitle("Inventory System - Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e){
            errorAlert("Modify Error", "Selection Error", "Nothing selected to modify");
        }
    }

    /**
     * this sends the user to the add parts screen
     * */
    @FXML
    private void addHandler(ActionEvent event) throws Exception{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setTitle("Inventory System - Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This deletes the selected part*/
    @FXML
    private void deleteHandler(ActionEvent event) {
        try {
            Part partDel = part_table.getSelectionModel().getSelectedItem();
            String content = "Are you sure you want to remove " + partDel.getName() + "?";
            if (confirmAlert("Delete?", content)) {
                deletePart(partDel);
                partTableReset();
            }
        }
        catch (Exception e){
            errorAlert("Delete Error", "Selection Error", "Nothing selected to delete");
        }
    }

    /**
     * This sends the user to the modify product page
     * */
    @FXML
    private void modifyHandlerPro(ActionEvent event){
        try {
            prodModifyID = pro_table.getSelectionModel().getSelectedItem().getId().getValue();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            stage.setTitle("Inventory System - Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e){
            errorAlert("Modify Error", "Selection Error", "Nothing selected to modify");
        }
    }

    /**
     * This sends the user to the add product page*/
    @FXML
    private void addHandlerPro(ActionEvent event) throws Exception{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setTitle("Inventory System - Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This deletes the selected product if the product has not parts associated with it*/
    @FXML
    private void deleteHandlerPro(ActionEvent event) {
        try {
            Product prodDel = pro_table.getSelectionModel().getSelectedItem();
            if(prodDel.getAllAssociatedParts().isEmpty()){
                String content = "Are you sure you want to remove " + prodDel.getName() + "?";
                if (confirmAlert("Delete?", content)) {
                    deleteProduct(prodDel);
                    proTableReset();
                }
            }else{
                errorAlert("Delete Error", "Selection Error", "This product has parts, please remove all parts before deleting the product.");
            }

        }
        catch (Exception e){
            errorAlert("Delete Error", "Selection Error", "Nothing selected to delete");
        }
    }

    /**
     * This Exits/ends the program*/
    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This Generates a error alert box
     *
     * @param content Is the main body of the alert
     * @param header Is the title (Larger text) of the alert
     * @param title is the title of the alert box displayed on the top left
     * */
    static void errorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This two functions return two variables with the ids of either the part or product */
    static int getPartModify(){
        return partModifyID;
    }
    static int getProdModify(){
        return prodModifyID;
    }

    /**This fills in the parts table with all the parts*/
    private void partTableFill(){
        part_table.setItems(getAllParts());
    }
    /**This fills in the products table with all the products*/
    private void proTableFill(){
        pro_table.setItems(getAllProducts());
    }

    /**This generates a confirmation alert that allows the user to continue based on "OK" or Cancel
     *
     * @param title is the title of the alert box located on the top of the screen
     * @param content is the main body of the alert box, this is where the main message to the user goes*/
    static boolean confirmAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**This resets the search and part table to default after a search has been done*/
    private void partTableReset(){
        search.setText("");
        partTableFill();
    }
    /**This resets the product search and product table to default after a search has been done*/
    private void proTableReset(){
        pro_search.setText("");
        proTableFill();
    }
}
