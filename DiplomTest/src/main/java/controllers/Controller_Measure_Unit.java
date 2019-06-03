package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modeltables.Tableview_Estimate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Measure_Unit {


    @FXML
    private TextField nameUnits;

    @FXML
    private Button addMeasureUnitButton;

    @FXML
    private Button deleteMeasureButton;

    @FXML
    void AddMeasureUnit(ActionEvent event) {

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL insertmeasureunit(?)");
            preparedStatement.setString(1,nameUnits.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeleteMeasureUnit(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL removemeasureunit(?)");
            preparedStatement.setString(1,nameUnits.getText());

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}

