package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Estimate {

    ErrorFormClass errorFormClass;
    @FXML
    private Button addTypeEstimateButton;

    @FXML
    private TextField nameTypePlacementTextField;

    @FXML
    private Button deleteTypeEstimateButton;

    @FXML
    void AddTypeEstimate(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "begin;\n" +
                    "INSERT INTO typeestimate(typeestimateid,nametype) \n" +
                    "VALUES (DEFAULT,?);\n" +
                    "commit;");
            preparedStatement.setString(1,nameTypePlacementTextField.getText());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();

        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void DeleteTypeEstimate(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "begin;\n" +
                    "DELETE FROM typeestimate\n" +
                    "WHERE nameType = ?;\n" +
                    "commit;");
            preparedStatement.setString(1,nameTypePlacementTextField.getText());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();

        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

}
