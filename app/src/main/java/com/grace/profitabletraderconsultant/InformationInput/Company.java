package com.grace.profitabletraderconsultant.InformationInput;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class Company {

    public String pushID;
    //CompanyInfo
    private String BusinessName;
    private String BusinessType;
    private String County;
    private String SubCounty;
    private String Phone;

    //ProductInfo
    public String Product;
    public String Price;

    public Company() {
    }

    public Company(String BusinessName, String BusinessType, String County, String SubCounty, String Phone) {
        this.BusinessName = BusinessName;
        this.BusinessType = BusinessType;
        this.County = County;
        this.SubCounty = SubCounty;
        this.Phone = Phone;
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

    public String getSubCounty() {
        return SubCounty;
    }

    public String getPhone() {
        return Phone;
    }
}
