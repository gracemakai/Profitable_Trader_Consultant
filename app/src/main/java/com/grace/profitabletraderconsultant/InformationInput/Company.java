package com.grace.profitabletraderconsultant.InformationInput;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class Company {

    public String Key;
    //CompanyInfo
    private String BusinessName;
    private String BusinessType;
    private String County;

    //ProductInfo
    public String Product;
    public String Price;

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    public void setBusinessType(String BusinessType) {
        this.BusinessType =BusinessType;
    }

    public void setCounty(String County) {
        this.County = County;
    }

    public Company() {
    }

    public Company(String BusinessName, String BusinessType, String County) {
        this.BusinessName = BusinessName;
        this.BusinessType = BusinessType;
        this.County = County;
    }

    public Company(String Product, String Price) {
        this.Product = Product;
        this.Price = Price;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public String getCounty() {
        return County;
    }


}
