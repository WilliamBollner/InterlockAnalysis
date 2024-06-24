package data;

import model.BoardMember;
import model.Company;
import model.NominatingBody;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public List<Object> readExcel(String filePath) throws IOException {
        List<Object> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Ignore header
                }
                String companyName = getCellValue(row.getCell(0));
                String companyAcronym = getCellValue(row.getCell(1));
                Company company = new Company(companyName, companyAcronym);

                String memberName = getCellValue(row.getCell(3));
                String memberPosition = getCellValue(row.getCell(4));
                BoardMember boardMember = new BoardMember(memberName, memberPosition, company);

                String body = getCellValue(row.getCell(5));
                NominatingBody nominatingBody = new NominatingBody(body, company);

                data.add(boardMember);
                data.add(nominatingBody);
            }
        }
        return data;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return "";
        }
    }
}