package modeltables;

public class Tableview_Scopeofwork {
    String nameWork,quantity, nameTypeWork, measureUnit, estimateId,employerFIO,dateExec,price,placementId;

    public Tableview_Scopeofwork(String nameWork, String quantity, String nameTypeWork, String measureUnit,
                                 String estimateId, String employerFIO, String dateExec,
                                 String price, String placementId) {
        this.nameWork = nameWork;
        this.quantity = quantity;
        this.nameTypeWork = nameTypeWork;
        this.measureUnit = measureUnit;
        this.estimateId = estimateId;
        this.employerFIO = employerFIO;
        this.dateExec = dateExec;
        this.price = price;
        this.placementId = placementId;
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNameTypeWork() {
        return nameTypeWork;
    }

    public void setNameTypeWork(String nameTypeWork) {
        this.nameTypeWork = nameTypeWork;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getEmployerFIO() {
        return employerFIO;
    }

    public void setEmployerFIO(String employerFIO) {
        this.employerFIO = employerFIO;
    }

    public String getDateExec() {
        return dateExec;
    }

    public void setDateExec(String dateExec) {
        this.dateExec = dateExec;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }
}
