package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    }

    @FXML
    void DeleteContract(ActionEvent event) {

    }

    @FXML
    void EditContract(ActionEvent event) {

    }

    @FXML
    void OpenClientForm(ActionEvent event) {

    }

    @FXML
    void OpenEmployersForm(ActionEvent event) {

    }

    @FXML
    void OpenEstimates(ActionEvent event) {

    }

    @FXML
    void OpenOrganizationForm(ActionEvent event) {

    }

    @FXML
    void OpenPlacementForm(ActionEvent event) {

    }
    private ObservableList olist = FXCollections.observableArrayList();

    private void FillEmployerCombobox()
    {

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT fio FROM employers");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                olist.add(rs.getString("fio"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        clientComboBox.setItems(olist);
    }
    @FXML
    public void setController_contract(Controller_Contract controller_contract) {
        this.controller_contract = controller_contract;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEmployerCombobox();
    }
}
