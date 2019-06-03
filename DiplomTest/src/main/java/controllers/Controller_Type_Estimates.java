package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Estimates {

    @FXML
    private TextField nameTypeEstimateTexxtField;

    @FXML
    private Button addTypeEstimateButton;

    @FXML
    private Button deleteTypeEstimateButton;

    @FXML
    void AddTypeEstimate(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL insertestimatetype(?)");
            preparedStatement.setString(1,nameTypeEstimateTexxtField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeleteTypeEstimate(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL removeestimatetype(?)");
            preparedStatement.setString(1,nameTypeEstimateTexxtField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

}
