package Model;

import java.util.ArrayList;

public class Inventory {

    private ArrayList <Part>    allParts;
    private ArrayList <Product> allProducts;

    public Inventory () {
        allProducts = new ArrayList <> ();
        allParts    = new ArrayList <> ();
    }

    public void addPart ( Part partToAdd ) {
        if ( partToAdd != null ) {
            allParts.add (partToAdd);
        }
    }

    public void addProduct ( Product productToAdd ) {
        if ( productToAdd != null ) {
            this.allProducts.add (productToAdd);
        }
    }

    public Part lookUpPart ( int partToLookUp ) {
        if ( !allParts.isEmpty () ) {
            for ( Part searchPart : allParts ) {
                if ( searchPart.getPartID () == partToLookUp ) {
                    return searchPart;
                }
            }
        }
        return null;
    }

    public Product lookUpProduct ( int productToSearch ) {
        if ( !allProducts.isEmpty () ) {
            for ( Product allProduct : allProducts ) {
                if ( allProduct.getProductID () == productToSearch ) {
                    return allProduct;
                }
            }
        }
        return null;
    }

    public void updatePart ( Part partToUpdate ) {
        for ( int i = 0; i < allParts.size (); i++ ) {
            if ( allParts.get (i).getPartID () == partToUpdate.getPartID () ) {
                allParts.set (i, partToUpdate);
                break;
            }
        }
    }

    public void updateProduct ( Product product ) {
        for ( int i = 0; i < allProducts.size (); i++ ) {
            if ( allProducts.get (i).getProductID () == product.getProductID () ) {
                allProducts.set (i, product);
                break;
            }
        }
    }

    public boolean deletePart ( Part partToDelete ) {
        for ( int i = 0; i < allParts.size (); i++ ) {
            if ( allParts.get (i).getPartID () == partToDelete.getPartID () ) {
                allParts.remove (i);
                break;
            } else {
                return false;

            }
        }
        return true;
    }

    public boolean deleteProduct ( Product productToDelete ) {
        for ( int i = 0; i < allProducts.size (); i++ ) {
            if ( allProducts.get (i).getProductID () == productToDelete.getProductID () ) {
                allProducts.remove (i);
                break;
            } else {
                return false;
            }
        }
        return true;
    }

    public ArrayList <Product> getAllProducts () {
        return allProducts;
    }

    public ArrayList <Part> getAllParts () {
        return allParts;
    }

    public int getPartListSize () {
        return allParts.size ();
    }

    public int getProductListSize () {
        return allProducts.size ();
    }

}

