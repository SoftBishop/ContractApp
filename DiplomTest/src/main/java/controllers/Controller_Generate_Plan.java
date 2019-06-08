package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class Controller_Generate_Plan {

    @FXML
    private DatePicker currentDateDatePicker;

    @FXML
    private DatePicker finalDateDatePicker;

    @FXML
    private Button createPlanButton;

    @FXML
    void CreatePlan(ActionEvent event) {
        LocalDate localDateCretion = currentDateDatePicker.getValue();
        String strDateCur= localDateCretion.toString();
        LocalDate localDateExec = finalDateDatePicker.getValue();
        String strDateFinish = localDateExec.toString();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                    "typeworks.typeworkname, measureunits.namemeasureunit AS MEASURE, estimates.estimateid, employers.fio AS EMPFIO,\n" +
                    "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                    "scopeofworkestimates.price AS price, placements.placementid AS placementid\n" +
                    "FROM scopeofworkestimates \n" +
                    "JOIN typeworks ON typeworks.typeworkid = scopeofworkestimates.typeworks\n" +
                    "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.measureunitid\n" +
                    "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid  \n" +
                    "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                    "JOIN placements ON placements.placementid = scopeofworkestimates.placement\n" +
                    "where dateExecution BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD');");
            preparedStatement.setString(1,nmaeTypeContractTextField.getText());
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

}
