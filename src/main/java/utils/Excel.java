package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

public class Excel {


    public static Object readFromExcel(String sheetName, int rowNo, int colNo) throws IOException, ParseException {
        //Create an object of File class to open Excel file
        File file = new File("test-output/data.xlsx");

        //Create an object of FileInputStream class to read an Excel file
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(inputStream);

        //Read sheet inside the workbook by its name
        Sheet worksheet = workbook.getSheet(sheetName);
        Row row = worksheet.getRow(rowNo);

        Cell header = worksheet.getRow(0).getCell(colNo);
        DataFormatter format = new DataFormatter();
        String headerValue = format.formatCellValue(header);

        Cell cell = row.getCell(colNo);
        CellType type = cell.getCellType();

        Object CellData = null;
        switch (type) {
            case STRING, NUMERIC:
                CellData = format.formatCellValue(cell);
                break;
            case BOOLEAN:
                boolean booleanCellData = cell.getBooleanCellValue();
                CellData = booleanCellData;
                break;
            case FORMULA:
                double formulaCellData = cell.getNumericCellValue();
                CellData = formulaCellData;
                break;
        }
        System.out.println("\nReading......\nType: " + type + "\nHeader: " + headerValue + "\nValue: " + CellData + "\n");

        return CellData;
    }
}
