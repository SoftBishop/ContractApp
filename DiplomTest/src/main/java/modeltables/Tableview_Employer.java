package modeltables;

public class Tableview_Employer {

    String FIO,dateHiring,telephoneNumber,position;

    public Tableview_Employer(String FIO, String dateHiring,
                              String telephoneNumber, String position) {
        this.FIO = FIO;
        this.dateHiring = dateHiring;
        this.telephoneNumber = telephoneNumber;
        this.position = position;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getDateHiring() {
        return dateHiring;
    }

    public void setDateHiring(String dateHiring) {
        this.dateHiring = dateHiring;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
