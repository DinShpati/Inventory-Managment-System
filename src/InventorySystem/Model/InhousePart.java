/**
 * C482 Software 1
 * Din Spataj
 * 001325332
 * 8/13/2020
 * */
package InventorySystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InhousePart extends Part{

    private IntegerProperty machineId;

    //Constructor
    public InhousePart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty min, IntegerProperty max, IntegerProperty machineId){
        super(id.get(), name.get(), price.get(), stock.get(), max.get(), min.get());
        this.machineId = machineId;
    }

    //Setter
    /**
     * @param machineId to set*/
    public void setMachineId(IntegerProperty machineId){
        this.machineId = machineId;
    }

    //Getter
    /**
     * @return the machine id*/
    public IntegerProperty getMachineId(){
        return machineId;
    }
}
