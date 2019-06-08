package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    ErrorFormClass errorFormClass;
    @FXML
    void AddOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN;" +
                    "INSERT INTO organizations(organizationid,nameorganization,\n" +
                    "\t\t\t\t\t\t  executivedirectorname,telephonenumber)\n" +
                    "\t\t\t\t\t\t  VALUES (DEFAULT,?,?,?);" +
                    "COMMIT;");
            preparedStatement.setString(1,nameOrganizationTextField.getText());
            preparedStatement.setString(2,execDirNameTextField.getText());
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        FillOrganizationTableView();
    }

    @FXML
    void DeleteOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM ORGANIZATIONS\n" +
                    "WHERE OrganizationID = ?;\n" +
                    "END;");
            preparedStatement.setInt(1,organizationId);

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        FillOrganizationTableView();
    }

    @FXML
    void EditOrgRow(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN;" +
                    "UPDATE organizations\n" +
                    "SET nameorganization = ?, " +
                    "executivedirectorname = ?, " +
                    "telephonenumber = ?\n" +
                    "WHERE ORGANIZATIONID = ?;" +
                    "COMMIT;");
            preparedStatement.setString(1,nameOrganizationTextField.getText());
            preparedStatement.setString(2,execDirNameTextField.getText());
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            preparedStatement.setInt(4,organizationId);

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        FillOrganizationTableView();
    }

    private ObservableList<Tableview_Organization> organizationList = FXCollections.observableArrayList();
    private void FillOrganizationTableView() {
        organizationList.clear();
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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        nameOrganizationCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("nameOrganization"));
        executiveDirNameCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("FIOExecDirector"));
        telephoneNumberCol.setCellValueFactory(new PropertyValueFactory<Tableview_Organization,String>("teleponeNumber"));

        organizationTableView.setItems(organizationList);
    }

    private int organizationId;
    private void GetDataByClick()
    {
        organizationTableView.setRowFactory( tv -> {
            TableRow<Tableview_Organization> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Tableview_Organization rowData = row.getItem();
                    try {

                        Connection connection;
                        connection = ConnectionPool.getDataSource().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("" +
                                "SELECT organizationID FROM ORGANIZATIONS " +
                                "where nameOrganization = ? and ExecutiveDirectorName = ?");
                        preparedStatement.setString(1,rowData.getFIOExecDirector());
                        preparedStatement.setString(2,rowData.getFIOExecDirector());
                        ResultSet rs = preparedStatement.executeQuery();
                        while(rs.next())
                        {
                            organizationId = Integer.parseInt(rs.getString("organizationID"));
                        }
                    }
                    catch (Exception ex)
                    {
                        errorFormClass = new ErrorFormClass();
                        errorFormClass.OpenErrorForm(ex);
                    }
                }
            });
            return row ;
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillOrganizationTableView();
        GetDataByClick();
    }
}
