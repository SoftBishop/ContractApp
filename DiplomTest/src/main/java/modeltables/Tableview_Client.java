package modeltables;

public class Tableview_Client {

    String fio, nameOrg;

    public Tableview_Client(String fio, String nameOrg) {
        this.fio = fio;
        this.nameOrg = nameOrg;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }
}
