package modeltables;

public class Tableview_Placement {

    String numPlacement,typePlacement,square,runOut;

    public Tableview_Placement(String numPlacement,
                               String typePlacement, String square, String runOut) {
        this.numPlacement = numPlacement;
        this.typePlacement = typePlacement;
        this.square = square;
        this.runOut = runOut;
    }

    public String getNumPlacement() {
        return numPlacement;
    }

    public void setNumPlacement(String numPlacement) {
        this.numPlacement = numPlacement;
    }

    public String getTypePlacement() {
        return typePlacement;
    }

    public void setTypePlacement(String typePlacement) {
        this.typePlacement = typePlacement;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getRunOut() {
        return runOut;
    }

    public void setRunOut(String runOut) {
        this.runOut = runOut;
    }
}
