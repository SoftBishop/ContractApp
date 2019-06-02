package modeltables;

public class Tableview_Contract {
    String contractID, contractType,contractDateOfCreation,
            contractDateExec, contractDateFinished ,nameClient, nameOrganization,
            nameEmployer, placementID, contractPrice;

    public Tableview_Contract(String contractID, String contractType, String contractDateOfCreation,
                              String contractDateExec, String contractDateFinished,
                              String nameClient, String nameOrganization,
                              String nameEmployer, String placementID, String contractPrice) {
        this.contractID = contractID;
        this.contractType = contractType;
        this.contractDateOfCreation = contractDateOfCreation;
        this.contractDateExec = contractDateExec;
        this.contractDateFinished = contractDateFinished;
        this.nameClient = nameClient;
        this.nameOrganization = nameOrganization;
        this.nameEmployer = nameEmployer;
        this.placementID = placementID;
        this.contractPrice = contractPrice;
    }

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractDateOfCreation() {
        return contractDateOfCreation;
    }

    public void setContractDateOfCreation(String contractDateOfCreation) {
        this.contractDateOfCreation = contractDateOfCreation;
    }

    public String getContractDateExec() {
        return contractDateExec;
    }

    public void setContractDateExec(String contractDateExec) {
        this.contractDateExec = contractDateExec;
    }

    public String getContractDateFinished() {
        return contractDateFinished;
    }

    public void setContractDateFinished(String contractDateFinished) {
        this.contractDateFinished = contractDateFinished;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameOrganization() {
        return nameOrganization;
    }

    public void setNameOrganization(String nameOrganization) {
        this.nameOrganization = nameOrganization;
    }

    public String getNameEmployer() {
        return nameEmployer;
    }

    public void setNameEmployer(String nameEmployer) {
        this.nameEmployer = nameEmployer;
    }

    public String getPlacementID() {
        return placementID;
    }

    public void setPlacementID(String placementID) {
        this.placementID = placementID;
    }

    public String getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
    }
}
