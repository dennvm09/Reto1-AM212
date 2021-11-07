package com.dennys.reto1_fvdm;

public class Publication {

    private String namePublication,locationPublication,initDatePublication,endDatePublication,nameBusiness, uriImageBusiness;

    public Publication(){}

    public Publication(String namePublication, String locationPublication, String initDatePublication, String endDatePublication, String nameBusiness, String uriImageBusiness) {
        this.namePublication = namePublication;
        this.locationPublication = locationPublication;
        this.initDatePublication = initDatePublication;
        this.endDatePublication = endDatePublication;
        this.nameBusiness = nameBusiness;
        this.uriImageBusiness = uriImageBusiness;
    }

    public String getNamePublication() {
        return namePublication;
    }

    public void setNamePublication(String name) {
        this.namePublication = name;
    }

    public String getLocationPublication() {
        return locationPublication;
    }

    public void setLocationPublication(String location) {
        this.locationPublication = location;
    }

    public String getInitDatePublication() {
        return initDatePublication;
    }

    public void setInitDatePublication(String initDatePublication) {
        this.initDatePublication = initDatePublication;
    }

    public String getEndDatePublication() {
        return endDatePublication;
    }

    public void setEndDatePublication(String end) {
        this.endDatePublication = end;
    }

    public String getNameBusiness() {
        return nameBusiness;
    }

    public void setNameBusiness(String business) {
        this.nameBusiness = business;
    }

    public String getUriImageBusiness() {
        return uriImageBusiness;
    }

    public void setUriImageBusiness(String uri) {
        this.uriImageBusiness = uri;
    }
}
