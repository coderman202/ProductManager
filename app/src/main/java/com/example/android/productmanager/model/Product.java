package com.example.android.productmanager.model;

/**
 * Created by Reggie on 21/07/2017.
 * Custom class to represent each product in the db
 */
public class Product {

    // Set the default id to be -1 so we can know if the object has been added to the db or not
    private int productID = -1;

    private String productName;

    private Category category;

    private float salePrice;

    private int quantity;

    private String quantityUnit;

    private Supplier supplier;

    private String imageUri;

    /**
     * Instantiates a new Product with an id which means it has been retrieved from the db.
     *
     * @param productID    the product id
     * @param productName  the product name
     * @param category     the category
     * @param salePrice    the sale price
     * @param quantity     the quantity
     * @param quantityUnit the quantity unit
     * @param supplier     the supplier
     * @param imageUri     the image Uri
     */
    public Product(int productID, String productName, Category category, float salePrice, int quantity, String quantityUnit, Supplier supplier, String imageUri) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.supplier = supplier;
        this.imageUri = imageUri;
    }

    /**
     * Instantiates a new Product without an id which means it is yet to be added to the db.
     *
     * @param productName  the product name
     * @param category     the category
     * @param salePrice    the sale price
     * @param quantity     the quantity
     * @param quantityUnit the quantity unit
     * @param supplier     the supplier
     * @param imageUri     the image Uri
     */
    public Product(String productName, Category category, float salePrice, int quantity, String quantityUnit, Supplier supplier, String imageUri) {
        this.productName = productName;
        this.category = category;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.supplier = supplier;
        this.imageUri = imageUri;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets sale price.
     *
     * @return the sale price
     */
    public float getSalePrice() {
        return salePrice;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets quantity unit.
     *
     * @return the quantity unit
     */
    public String getQuantityUnit() {
        return quantityUnit;
    }

    /**
     * Gets supplier.
     *
     * @return the supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }


    /**
     * Gets image uri.
     *
     * @return the image uri
     */
    public String getImageUri() {
        return imageUri;
    }

    /**
     * Sets product id.
     *
     * @param productID the product id
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * A method which returns true if the object is in the db already.
     *
     * @return the boolean
     */
    public boolean isInDB(){
        return this.productID != -1;
    }
}
