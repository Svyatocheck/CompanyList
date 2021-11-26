package com.example.dbData.Model;
import java.io.Serializable;

public class Note implements Serializable {

    private String companyName;
    private String founder;
    private String product;

    public Note(String companyName, String founder, String product) {
        this.companyName = companyName;
        this.founder = founder;
        this.product = product;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct (String product) {
        this.product = product;
    }

    public static class ChildClass implements Serializable {

        public ChildClass() {}
    }
}