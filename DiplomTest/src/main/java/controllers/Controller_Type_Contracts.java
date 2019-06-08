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

public class Controller_Type_Contracts {

    ErrorFormClass errorFormClass;
    @FXML
    private TextField nmaeTypeContractTextField;

    @FXML
    private Button addTypeContractButton;

    @FXML
    private Button deleteTypeWorkButton;

    @FXML
    void AddTypeContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "begin;\n" +
                    "DELETE FROM typecontracts\n" +
                    "WHERE nametypecontract = ?\n" +
                    "commit;");
            preparedStatement.setString(1,nmaeTypeContractTextField.getText());
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
    void DeleteTypeContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "begin;\n" +
                    "INSERT INTO typecontracts(typecontractid,nametypecontract)VALUES \n" +
                    "(DEFAULT,?);\n" +
                    "commit;");
            preparedStatement.setString(1,nmaeTypeContractTextField.getText());
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
