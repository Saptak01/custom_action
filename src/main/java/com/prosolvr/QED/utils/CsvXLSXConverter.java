package com.prosolvr.QED.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class CsvXLSXConverter {

    private final String csvContent;
    private final List<List<String>> cells;

    public CsvXLSXConverter(String csvContent) {
        this.csvContent = csvContent;
        this.cells = new ArrayList<>();
        parse();
    }

    private void parse() {
        Scanner textToParse = new Scanner(csvContent);
        while(textToParse.hasNextLine()) {
            String row = textToParse.nextLine();
            String[] rowCells = row.split(",");
            List<String> rowParsed = new ArrayList<>(Arrays.asList(rowCells));
            cells.add(rowParsed);
        }
    }

    public String generateFile() {
        Workbook wb = new XSSFWorkbook();
        Sheet finalSheet = wb.createSheet("Causes");
        for(int i = 0; i < cells.size(); i++) {
            List<String> currentRow = cells.get(i);
            Row newRow = finalSheet.createRow(i);
            for(int j = 0; j < currentRow.size(); j++) {
                Cell cell = newRow.createCell(j);
                if(i > 0 && j < 2) {
                    cell.setCellValue(Integer.parseInt(currentRow.get(j)));
                } else {
                    cell.setCellValue(currentRow.get(j));
                }
            }
        }

        String uniqueFileId = UUID.randomUUID().toString();

        File ndir = new File("./" + uniqueFileId);
        if(ndir.mkdir()) {
            try(OutputStream fo = new FileOutputStream("./" + uniqueFileId + "/causes.xlsx")) {
                wb.write(fo);
                wb.close();
                return uniqueFileId;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String readFromExcelAndEncodeBase64(String uniqueFolderPath) {
        try(FileInputStream fis = new FileInputStream("./" + uniqueFolderPath + "/causes.xlsx")) {
            String encodedFile = new String(Base64.getEncoder().encode(fis.readAllBytes()));
            File causesFile = new File("./" + uniqueFolderPath + "/causes.xlsx");
            File uniqueCauseFolder = new File("./" + uniqueFolderPath );
            causesFile.delete();
            uniqueCauseFolder.delete();
            return encodedFile;
        } catch (Exception e) {
            return "";
        }
    }
}
