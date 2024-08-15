package Model;
/**
 * Class for outsourced part
 * @author Aydan Harrison
 */
public class Outsourced extends Part {

    /** OutSource Company Name*/
    private String companyName;

    /** Constructor for OutSource Parts
     * @param id - part id
     * @param name - part name
     * @param price - part price
     * @param stock - amount of stock of part
     * @param min - minimum amount of a part
     * @param max - maximum amount of a part
     * @param companyName - company name where part from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**This gets the company name.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This sets the company name.
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}