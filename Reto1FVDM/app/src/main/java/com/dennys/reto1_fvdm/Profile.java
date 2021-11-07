package com.dennys.reto1_fvdm;



public class Profile {

    private String nameBusiness;
    private String uriImageBusiness;
    private String descriptionBusiness;

    public Profile(){

    }
    public Profile(String nameBusiness, String descriptionBusiness, String uriImageBusiness) {
        this.nameBusiness = nameBusiness;
        this.uriImageBusiness = uriImageBusiness;
        this.descriptionBusiness = descriptionBusiness;
    }

    public String getNameBusiness() {
        return nameBusiness;
    }

    public String getDescriptionBusiness() {
        return descriptionBusiness;
    }

    public String getUriImageBusiness() {
        return uriImageBusiness;
    }

    public void setNameBusiness(String name) {
        this.nameBusiness = name;
    }

    public void setUriImageBusiness(String uri) {
        this.uriImageBusiness = uri;
    }

    public void setDescriptionBusiness(String description) {
        this.descriptionBusiness = description;
    }
}