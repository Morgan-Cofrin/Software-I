package Model;

public class InHouse extends Part {

    private int machineID;

    //Constructor
    public InHouse (int partID, String partName, double partPrice, int numInStock, int min, int max, int machineID) {

        setPartID(partID);
        setPartName(partName);
        setPartPrice(partPrice);
        setPartInStock(numInStock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);

    }

    //Mutator
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    //Accessor
    public int getMachineID() {
        return this.machineID;
    }

}
