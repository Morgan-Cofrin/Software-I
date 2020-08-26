package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    //Properties
    private ObservableList < Part > associatedParts = FXCollections.observableArrayList ();

    private int    productID;
    private String productName;
    private double productPrice   = 0.0;
    private int    productInStock = 0;
    private int    min;
    private int    max;

    //Constructor
    public Product ( int productID, String productName, double productPrice, int productInStock, int min, int max ) {

        setProductID (productID);
        setProductName (productName);
        setProductPrice (productPrice);
        setProductInStock (productInStock);
        setMin (min);
        setMax (max);

    }

    //Setters
    public void setProductID ( int productID ) {
        this.productID = productID;
    }
    public void setProductName ( String productName ) {
        this.productName = productName;
    }
    public void setProductPrice ( double productPrice ) {
        this.productPrice = productPrice;
    }
    public void setProductInStock ( int productInStock ) {
        this.productInStock = productInStock;
    }
    public void setMin ( int min ) {
        this.min = min;
    }
    public void setMax ( int max ) {
        this.max = max;
    }

    //Getters
    public int getProductID () {
        return productID;
    }
    public String getProductName () {
        return productName;
    }
    public double getProductPrice () {
        return productPrice;
    }
    public int getProductInStock () {
        return productInStock;
    }
    public int getMin () {
        return min;
    }
    public int getMax () {
        return max;
    }


    public void addAssociatedPart ( Part partToAdd ) {
        associatedParts.add (partToAdd);
    }

    public ObservableList < Part > getAssociatedParts () {
        return associatedParts;
    }

    public boolean deleteAssociatedPart ( int partToDelete ) {
        for ( int i = 0; i < associatedParts.size (); i++ ) {
            if ( associatedParts.get (i).getPartID () == partToDelete ) {
                associatedParts.remove (i);
                return true;
            }
        }
        return false;
    }

    public Part lookupAssociatedPart ( int partToSearch ) {
        for ( Part associatedPart : associatedParts ) {
            if ( associatedPart.getPartID () == partToSearch ) {
                return associatedPart;
            }
        }
        return null;
    }

    public int getPartsListSize () {
        return associatedParts.size ();
    }

}
