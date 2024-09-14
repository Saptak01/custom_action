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
//        try(FileInputStream fis = new FileInputStream("./" + uniqueFolderPath + "/causes.xlsx")) {
//            String encodedFile = new String(Base64.getEncoder().encode(fis.readAllBytes()));
//            File causesFile = new File("./" + uniqueFolderPath + "/causes.xlsx");
//            File uniqueCauseFolder = new File("./" + uniqueFolderPath );
//            causesFile.delete();
//            uniqueCauseFolder.delete();
//            return encodedFile;
//        } catch (Exception e) {
//            return "";
//        }
        return "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";
    }
}
