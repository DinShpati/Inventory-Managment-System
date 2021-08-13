/**
 * C482 Software 1
 * Din Spataj
 * 001325332
 * 8/13/2020
 * */
package InventorySystem.Model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This adds a new part to the inventory
     * @param newPart the newpart that is being added*/
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * This adds a new product to the inventory
     * @param newProduct the newProduct that is being added*/
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**Looking up parts and products based on if the user inputs a string(product name) ore integer(product id)*/
    /**@param partId Searches the inventory for a part with the id of partId*/
    public static Part lookupPart(int partId){
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    /**@param productId Searches the inventory for a product with the id of productId*/
    public static Product lookupProduct(int productId){
        for (Product product : allProducts) {
            if (productId == product.getId().getValue()) {
                return product;
            }
        }
        return null;
    }
    /**@param partName searches the inventory for a part with a name of partName*/
    public static Part lookupPart(String partName){
        for(Part part : allParts){
            if(partName.equals(part.getName())){
                return part;
            }
        }
        return null;
    }
    /**@param productName searches the inventory for a product with a name of productName*/
    public static Product lookupProduct(String productName){
        for(Product product : allProducts){
            if(productName.equals(product.getName().getValue())){
                return product;
            }
        }
        return null;
    }

    /**
     * Updating the Parts and the products*/
    /**@param index the location of the selected part
     * @param selectedPart the selected Part*/
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**@param index the location of the selected part
     * @param selectedProduct the selected Part*/
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**Deleting the parts and the products*/
    /**@param selectedPart - the selected part that is going to be deleted*/
    public static boolean deletePart(Part selectedPart){
       return allParts.remove(selectedPart);
    }
    /**@param selectedProduct - The selected product that is going to be deleted*/
    public static boolean deleteProduct(Product selectedProduct){
       return allProducts.remove(selectedProduct);
    }

    /**Getting all the parts and the products*/
    /**@return all the parts in the inventory*/
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**@return all the products in the inventory*/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
