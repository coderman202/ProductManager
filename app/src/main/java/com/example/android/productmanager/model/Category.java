package com.example.android.productmanager.model;

/**
 * Created by Reggie on 21/07/2017.
 * Custom class to represent the categories
 */
public class Category {

    // Set the default id to be -1 so we can know if the object has been added to the db or not
    private int categoryID = -1;

    private String categoryName;

    private int iconID;

    /**
     * Instantiates a new Category.
     *
     * @param categoryID   the category id
     * @param categoryName the category name
     */
    public Category(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    /**
     * Instantiates a new Category with an id which means it has been retrieved from the db.
     *
     * @param categoryID   the category id
     * @param categoryName the category name
     * @param iconID       the icon id
     */
    public Category(int categoryID, String categoryName, int iconID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.iconID = iconID;
    }

    /**
     * Instantiates a new Category without an id which means it is yet to be added to the db.
     *
     * @param categoryName the category name
     * @param iconID       the icon id
     */
    public Category(String categoryName, int iconID) {
        this.categoryName = categoryName;
        this.iconID = iconID;
    }

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Gets icon id.
     *
     * @return the icon id
     */
    public int getIconID() {
        return iconID;
    }

    /**
     * Sets category id.
     *
     * @param categoryID the category id
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * A method which returns true if the object is in the db already.
     *
     * @return the boolean
     */
    public boolean isInDB(){
        return this.categoryID != -1;
    }
}
