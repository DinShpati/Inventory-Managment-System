/**
 * C482 Software 1
 * Din Spataj
 * 001325332
 * 8/13/2020
 * */
package InventorySystem.Model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private IntegerProperty id, stock, min, max;
    private DoubleProperty price;
    private StringProperty name;

    //constructor
    public Product(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty max, IntegerProperty min){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
    }

    //setters
    /**
     * @param id to set*/
    public void setId(IntegerProperty id){
        this.id = id;
    }
    /**
     * @param name to set*/
    public void setName(StringProperty name){
        this.name = name;
    }
    /**
     * @param price to set*/
    public void setPrice(DoubleProperty price){
        this.price = price;
    }
    /**
     * @param stock to set*/
    public void setStock(IntegerProperty stock){
        this.stock = stock;
    }
    /**
     * @param max to set*/
    public void setMax(IntegerProperty max){
        this.max = max;
    }
    /**
     * @param min to set*/
    public void setMin(IntegerProperty min){
        this.min = min;
    }

    //Getters
    /**
     * @return the id*/
    public IntegerProperty getId(){
        return id;
    }
    /**
     * @return the name*/
    public StringProperty getName(){
        return name;
    }
    /**
     * @return the price*/
    public DoubleProperty getPrice(){
        return price;
    }
    /**
     * @return the stock*/
    public IntegerProperty getStock(){
        return stock;
    }
    /**@return the max*/
    public IntegerProperty getMax(){
        return max;
    }
    /** @return the min*/
    public IntegerProperty getMin(){
        return min;
    }

    /**
     * Getting, Deleting, and Adding associated parts to the Product */
    /**
     * @param part - the associate part that is going to be added*/
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
    /**
     * @param selectAssociatedPart - The associated part that is going to be removed from the product*/
    public boolean deleteAssociatedPart(Part selectAssociatedPart){
        return associatedParts.remove(selectAssociatedPart);
    }
    /**
     * @return all the associated parts*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
