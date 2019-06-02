package modeltables;

public class Tableview_Organization {
    String nameOrganization, FIOExecDirector,teleponeNumber;

    public Tableview_Organization(String nameOrganization,
                                  String FIOExecDirector, String teleponeNumber) {
        this.nameOrganization = nameOrganization;
        this.FIOExecDirector = FIOExecDirector;
        this.teleponeNumber = teleponeNumber;
    }

    public String getNameOrganization() {
        return nameOrganization;
    }

    public void setNameOrganization(String nameOrganization) {
        this.nameOrganization = nameOrganization;
    }

    public String getFIOExecDirector() {
        return FIOExecDirector;
    }

    public void setFIOExecDirector(String FIOExecDirector) {
        this.FIOExecDirector = FIOExecDirector;
    }

    public String getTeleponeNumber() {
        return teleponeNumber;
    }

    public void setTeleponeNumber(String teleponeNumber) {
        this.teleponeNumber = teleponeNumber;
    }
}
