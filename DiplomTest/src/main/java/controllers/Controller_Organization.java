package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modeltables.Tableview_Organization;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Organization implements Initializable {

    @FXML
    private TableView<Tableview_Organization> organizationTableView;

    @FXML
    private TableColumn<Tableview_Organization, String> nameOrganizationCol;

    @FXML
    private TableColumn<Tableview_Organization, String> executiveDirNameCol;

    @FXML
    private TableColumn<Tableview_Organization, String> telephoneNumberCol;

    @FXML
    private TextField nameOrganizationTextField;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private TextField execDirNameTextField;

    @FXML
    private Button deleteOrganizatonButton;

    @FXML
    private Button editOrgButton;

    @FXML
    private Button AddOrganizationRow;

    @FXML
    void AddOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL insertorganization(?,?,?)");
            preparedStatement.setString(1,nameOrganizationTextField.getText());
            preparedStatement.setString(2,execDirNameTextField.getText());
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeleteOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL removeorganization(?,?,?)");
            preparedStatement.setString(1,nameOrganizationTextField.getText());
            preparedStatement.setString(2,execDirNameTextField.getText());
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void EditOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL editeorganization(?,?,?)");
            preparedStatement.setString(1,nameOrganizationTextField.getText());
            preparedStatement.setString(2,execDirNameTextField.getText());
            preparedStatement.setString(3,telephoneNumberTextField.getText());

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private ObservableList<Tableview_Organization> organizationList = FXCollections.observableArrayList();
    private void FillOrganizationTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT organizations.nameorganization AS NAMEORG,\n" +
                    "organizations.executivedirectorname AS DIRNAME, organizations.telephonenumber AS TELNUM \n" +
                    "FROM organizations ");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                organizationList.add(new Tableview_Organization(rs.getString("NAMEORG"),
                        rs.getString("DIRNAME"),
                        rs.getString("TELNUM")));

            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        nameOrganizationCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("nameOrganization"));
        executiveDirNameCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("FIOExecDirector"));
        telephoneNumberCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("teleponeNumber"));

        organizationTableView.setItems(organizationList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillOrganizationTableView();
    }
}
