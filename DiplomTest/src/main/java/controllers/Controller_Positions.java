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

public class Controller_Positions {

    @FXML
    private TextField namePositionTextField;

    @FXML
    private Button addPositionButton;

    @FXML
    private Button deletePositionButton;

    ErrorFormClass errorFormClass;
    @FXML
    void AddPosition(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "INSERT INTO positions(positionid,nameposition)\n" +
                    "VALUES (DEFAULT,?);\n" +
                    "COMMIT;");
            preparedStatement.setString(1,namePositionTextField.getText());

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
    void DeletePosition(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM positions\n" +
                    "WHERE nameposition = ?;\n" +
                    "COMMIT;");
            preparedStatement.setString(1,namePositionTextField.getText());
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
