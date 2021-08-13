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

public class OutsourcedPart extends Part{
    private StringProperty companyName;

    //Constructor
    public OutsourcedPart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty min, IntegerProperty max, StringProperty companyName){
        super(id.get(), name.get(), price.get(), stock.get(), max.get(), min.get());
        this.companyName = companyName;
    }

    //Setter
    /**@param companyName the comapnyName to set*/
    public void setCompanyName(StringProperty companyName){
        this.companyName = companyName;
    }

    //Getter
    /**@return the companyName*/
    public StringProperty getCompanyName(){
        return companyName;
    }
}
