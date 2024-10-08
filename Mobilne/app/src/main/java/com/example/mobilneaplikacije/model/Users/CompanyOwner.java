package com.example.mobilneaplikacije.model.Users;

import com.example.mobilneaplikacije.model.Company;
import com.example.mobilneaplikacije.model.Users.BaseUser;
import com.example.mobilneaplikacije.model.enums.UserRole;

public class CompanyOwner extends BaseUser {
    private Company company;

    public CompanyOwner(Company company) {
        this.company = company;
    }

    public CompanyOwner(String email, String password, String firstName, String lastName, String address, String phone, UserRole role, Company company) {
        super(email, password, firstName, lastName, address, phone, role);
        this.company = company;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
