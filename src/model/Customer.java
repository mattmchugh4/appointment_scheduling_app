package model;
/**
 * The customer class creates a "Customer" object.
 *
 * @author Matt McHugh
 *
 */
public class Customer {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public int id;
    public String name;
    public String address;
    public String zip;
    public String phone;
    public int division;

    /**
     * This is the constructor for the customer class. It creates a new instance of a customer object.
     * @param id
     * @param name
     * @param address
     * @param zip
     * @param phone
     * @param division
     */
    public Customer(int id, String name, String address, String zip, String phone, int division) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.division = division;
    }
}
