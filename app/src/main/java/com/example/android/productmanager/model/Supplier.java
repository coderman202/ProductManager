package com.example.android.productmanager.model;

/**
 * Created by Reggie on 21/07/2017.
 * A custom class to represent suppliers.
 */
public class Supplier {

    // Set the default id to be -1 so we can know if the object has been added to the db or not
    private int supplierID = -1;

    private String supplierName;

    private String address;

    private String email;

    private String phone;


    /**
     * Instantiates a new Supplier with an id which means it has been retrieved from the db.
     *
     * @param supplierID   the supplier id
     * @param supplierName the supplier name
     * @param address      the address
     * @param email        the email
     * @param phone        the phone
     */
    public Supplier(int supplierID, String supplierName, String address, String email, String phone) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Instantiates a new Supplier without an id which means it is yet to be added to the db.
     *
     * @param supplierName the supplier name
     * @param address      the address
     * @param email        the email
     * @param phone        the phone
     */
    public Supplier(String supplierName, String address, String email, String phone) {
        this.supplierName = supplierName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Gets supplier id.
     *
     * @return the supplier id
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * Gets supplier name.
     *
     * @return the supplier name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets supplier id.
     *
     * @param supplierID the supplier id
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * A method which returns true if the object is in the db already.
     *
     * @return the boolean
     */
    public boolean isInDB(){
        return this.supplierID != -1;
    }
}
