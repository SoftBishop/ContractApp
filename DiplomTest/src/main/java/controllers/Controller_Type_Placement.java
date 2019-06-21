package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Placement {

    @FXML
    private TextField nameTypePlacementTextField;

    @FXML
    private Button addTypePlacementButton;

    @FXML
    private Button deleteTypePlacmentButton;

    ErrorFormClass errorFormClass;
    @FXML
    void AddTypePlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "INSERT INTO typeestimate(typeestimateid,nametype) \n" +
                    "VALUES (DEFAULT,?);\n" +
                    "COMMIT;");

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
    void DeleteTypePlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM typeestimate\n" +
                    "WHERE nameType = ?;\n" +
                    "COMMIT;");
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
