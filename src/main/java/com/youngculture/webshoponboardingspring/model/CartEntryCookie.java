package com.youngculture.webshoponboardingspring.model;

//model for anonymous cart
public class CartEntryCookie {

    private String productName;
    private Integer quantity;

    public CartEntryCookie() {
        quantity = 1;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
