package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller_Contract_Editor implements Initializable {


    @FXML
    private Controller_Contract controller_contract;

    @FXML
    public void setController_contract(Controller_Contract controller_contract) {
        this.controller_contract = controller_contract;
    }

    @FXML
    private TextArea contractTextField;

    private void SetTextContractTextField(String text)
    {
        contractTextField.setText(text);
    }

    @FXML
    private DatePicker dateOfCreationContractDatePicker;

    private void SetDateOfCreationContractDatePicker(String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfCreationContractDatePicker.setValue(localDate);
    }

    @FXML
    private DatePicker dateOfExecContractDatePicker;

    private void SetDateOfExecContractDatePickerString (String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfExecContractDatePicker.setValue(localDate);
    }

    @FXML
    private DatePicker dateOfFinishContractDatePicker;

    private void SetDateOfFinishContractDatePicker (String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfFinishContractDatePicker.setValue(localDate);
    }

    @FXML
    private ComboBox<?> typeContractComboBox;

    private void SetTextTypeContractComboBox(String text)
    {
        typeContractComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> clientComboBox;

    private void SetTextClientComboBox(String text)
    {
        clientComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> organizationComboBox;

    private void SetTextOrganizationComboBox(String text)
    {
        organizationComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> employerComboBox;

    private void SetTextEmployerComboBox(String text)
    {
        employerComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> placementCombobox;

    private void SetTextPlacementCombobox(String text)
    {
        placementCombobox.getEditor().setText(text);
    }

    @FXML
    private TextField priceTextField;

    private void SetTextTextField(String text)
    {
        priceTextField.setText(text);
    }

    public void SetElements(String textContract, String creationDate,
                            String dateExec, String dateFinish,String nameType,
                            String clientName, String nameOrg, String nameEmployer,
                            String namePlacement,String price)
    {
        SetTextContractTextField(textContract);
        SetDateOfCreationContractDatePicker(creationDate);
        SetDateOfExecContractDatePickerString(dateExec);
        SetDateOfFinishContractDatePicker(dateFinish);
        SetTextTypeContractComboBox(nameType);
        SetTextClientComboBox(clientName);
        SetTextOrganizationComboBox(nameOrg);
        SetTextEmployerComboBox(nameEmployer);
        SetTextPlacementCombobox(namePlacement);
        SetTextTextField(price);
    }

    @FXML
    private Button clientFormButton;

    @FXML
    private Button organizationFormButton;

    @FXML
    private Button employersFormButton;

    @FXML
    private Button placementFormButton;

    @FXML
    private Button deleteContractButton;

    @FXML
    private Button EditContractButton;

    @FXML
    private Button AddContractButton;

    @FXML
    private Button estimateOpenButton;

    @FXML
    void AddContract(ActionEvent event) {

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL insertContract(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1,Integer.parseInt(contractTextField.getText()));
            java.sql.Date sqlDateCreation = java.sql.Date.valueOf( dateOfCreationContractDatePicker.getValue() );
            preparedStatement.setDate(2,sqlDateCreation);
            java.sql.Date sqlDateExec= java.sql.Date.valueOf( dateOfExecContractDatePicker.getValue());
            preparedStatement.setDate(3,sqlDateExec);
            java.sql.Date sqlDateExpire= java.sql.Date.valueOf( dateOfFinishContractDatePicker.getValue());
            preparedStatement.setDate(4,sqlDateExpire);
            preparedStatement.setInt(5,Integer.parseInt(priceTextField.getText()));
            preparedStatement.setString(6,organizationComboBox.getEditor().getText());
            preparedStatement.setString(7,clientComboBox.getEditor().getText());
            preparedStatement.setString(8,employerComboBox.getEditor().getText());
            preparedStatement.setString(9,typeContractComboBox.getEditor().getText());
            preparedStatement.setInt(10,Integer.parseInt(placementCombobox.getEditor().getText()));
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
  /*  java.sql.Date sqlDate = java.sql.Date.valueOf( dateHiringDatePicker.getValue() );
            preparedStatement.setDate(2,sqlDate);*/

    @FXML
    void DeleteContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL deleteContract(?)");
            preparedStatement.setInt(1,Integer.parseInt(contractTextField.getText()));
            ResultSet rs = preparedStatement.executeQuery();
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void EditContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL editEstimate(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1,Integer.parseInt(contractTextField.getText()));
            java.sql.Date sqlDateCreation = java.sql.Date.valueOf( dateOfCreationContractDatePicker.getValue() );
            preparedStatement.setDate(2,sqlDateCreation);
            java.sql.Date sqlDateExec= java.sql.Date.valueOf( dateOfExecContractDatePicker.getValue());
            preparedStatement.setDate(3,sqlDateExec);
            java.sql.Date sqlDateExpire= java.sql.Date.valueOf( dateOfFinishContractDatePicker.getValue());
            preparedStatement.setDate(4,sqlDateExpire);
            preparedStatement.setInt(5,Integer.parseInt(priceTextField.getText()));
            preparedStatement.setString(6,organizationComboBox.getEditor().getText());
            preparedStatement.setString(7,clientComboBox.getEditor().getText());
            preparedStatement.setString(8,employerComboBox.getEditor().getText());
            preparedStatement.setString(9,typeContractComboBox.getEditor().getText());
            preparedStatement.setInt(10,Integer.parseInt(placementCombobox.getEditor().getText()));
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenClientForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_client.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenEmployersForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_employer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

    private Controller_Scopeofwork_Estimate controller_scopeofwork_estimate;



    @FXML
    void OpenEstimates(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_estimate.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenOrganizationForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_oraganization.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenPlacementForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_placement.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
    private ObservableList employersList = FXCollections.observableArrayList();
    private ObservableList typesList = FXCollections.observableArrayList();
    private ObservableList organizationsList = FXCollections.observableArrayList();
    private ObservableList placementList = FXCollections.observableArrayList();
    private ObservableList clientList = FXCollections.observableArrayList();

    private void FillEmployerCombobox()
    {
        employersList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT fio FROM employers");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                employersList.add(rs.getString("fio"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        employerComboBox.setItems(employersList);
    }
    private void FillOrganizationComboBox()
    {
        organizationsList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT nameorganization FROM ORGANIZATIONS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                organizationsList.add(rs.getString("nameorganization"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        organizationComboBox.setItems(organizationsList);
    }

    private void FillTypesComboBox()
    {
        typesList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nametypecontract FROM TYPECONTRACTS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                typesList.add(rs.getString("nametypecontract"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        typeContractComboBox.setItems(typesList);
    }

    private void FillPlacementComboBox()
    {
        placementList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT placementid FROM placements");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                placementList.add(rs.getString("placementid"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        placementCombobox.setItems(placementList);
    }

    private void FillClientComboBox()
    {
        clientList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT fio FROM clients");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                clientList.add(rs.getString("fio"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        clientComboBox.setItems(clientList);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEmployerCombobox();
        FillTypesComboBox();
        FillOrganizationComboBox();
        FillPlacementComboBox();
        FillClientComboBox();
    }
}
