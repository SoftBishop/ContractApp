package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Controller_Generate_Plan {

    ErrorFormClass errorFormClass;


    @FXML
    private DatePicker currentDateDatePicker;

    @FXML
    private DatePicker finalDateDatePicker;

    @FXML
    private Button createPlanButton;

    @FXML
    void CreatePlan(ActionEvent event) {
        LocalDate localDateCretion = currentDateDatePicker.getValue();
        String strDateCur= localDateCretion.toString();
        LocalDate localDateExec = finalDateDatePicker.getValue();
        String strDateFinish = localDateExec.toString();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                    "typeworks.typeworkname, measureunits.namemeasureunit AS MEASURE, estimates.estimateid, " +
                    "employers.fio AS EMPFIO,\n" +
                    "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                    "scopeofworkestimates.price AS price, placements.placementid AS placementid\n" +
                    "FROM scopeofworkestimates \n" +
                    "JOIN typeworks ON typeworks.typeworkid = scopeofworkestimates.typeworks\n" +
                    "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.measureunitid\n" +
                    "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid  \n" +
                    "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                    "JOIN placements ON placements.placementid = scopeofworkestimates.placement\n" +
                    "where dateExecution BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD');");
            preparedStatement.setString(1,strDateCur);
            preparedStatement.setString(2,strDateFinish);
            ResultSet rs = preparedStatement.executeQuery();

            int i = 2;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook
                    .createSheet("План работ ");
            XSSFRow row = spreadsheet.createRow(1);
            XSSFCell cell;
            cell=row.createCell(1);
            cell.setCellValue("Наименование работ");
            cell=row.createCell(2);
            cell.setCellValue("Количество");
            cell=row.createCell(3);
            cell.setCellValue("Ед.ч.");
            cell=row.createCell(4);
            cell.setCellValue("Рабочий");
            cell=row.createCell(5);
            cell.setCellValue("Дата Выполнения");
            cell=row.createCell(6);
            cell.setCellValue("Помещение");
            cell=row.createCell(7);
            cell.setCellValue("Сделано?");

            while(rs.next())
            {
                row=spreadsheet.createRow(i);
                cell=row.createCell(1);
                cell.setCellValue(rs.getString("NAMEWORK"));
                cell=row.createCell(2);
                cell.setCellValue(rs.getString("QUANTITY"));
                cell=row.createCell(3);
                cell.setCellValue(rs.getString("MEASURE"));
                cell=row.createCell(4);
                cell.setCellValue(rs.getString("EMPFIO"));
                cell=row.createCell(5);
                cell.setCellValue(rs.getString("DATEEXECUTION"));
                cell = row.createCell(6);
                cell.setCellValue(rs.getString("placementid"));
                i++;
            }
            FileOutputStream out = new FileOutputStream(
                    new File("План работ.xlsx"));
            workbook.write(out);
            out.close();

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
