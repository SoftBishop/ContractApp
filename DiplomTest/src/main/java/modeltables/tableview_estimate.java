package modeltables;

public class tableview_estimate {

    String estimateID, dateCreation, typeName, totalPrice, contractID;

    public tableview_estimate(String estimateID, String dateCreation,
                              String typeName, String totalPrice, String contractID) {
        this.estimateID = estimateID;
        this.dateCreation = dateCreation;
        this.typeName = typeName;
        this.totalPrice = totalPrice;
        this.contractID = contractID;
    }

    public String getEstimateID() {
        return estimateID;
    }

    public void setEstimateID(String estimateID) {
        this.estimateID = estimateID;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }
}
