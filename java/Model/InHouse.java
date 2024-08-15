package Model;
/**
 * Class for in-house part
 * @author Aydan Harrison
 */
public class InHouse extends Part {

    /** In-house Machine ID*/
    private int machineId;

    /** Constructor for In-House Parts
     * @param id - part id
     * @param name - part name
     * @param price - part price
     * @param stock - amount of stock of part
     * @param min - minimum amount of a part
     * @param max - maximum amount of a part
     * @param machineId - machine id of a part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }



    /**This gets machine id.
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**This sets machine id.
     * @param machineId the machine id to set
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

}