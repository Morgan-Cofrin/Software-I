package Model;

public class Outsourced extends Part {

    private String companyName;

    //Constructor
    public Outsourced (int partID, String partName, double partPrice, int numInStock, int min, int max, String companyName ) {

        setPartID (partID);
        setPartName (partName);
        setPartPrice (partPrice);
        setPartInStock (numInStock);
        setMin (min);
        setMax (max);
        setCompanyName (companyName);

    }

    //Mutator
    public void setCompanyName ( String companyName ) {
        this.companyName = companyName;
    }

    //Accessor
    public String getCompanyName () {
        return companyName;
    }

}
