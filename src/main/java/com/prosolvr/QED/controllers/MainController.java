package com.prosolvr.QED.controllers;

import com.prosolvr.QED.models.RCARequestBody;
import com.prosolvr.QED.utils.CsvXLSXConverter;
import com.prosolvr.QED.utils.QEDProblemCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping(path = "/getRCALink", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRCADiagramLink() {
//        try {
//            String problemTitle = body.getTitle();
//            String problemDescription = body.getDescription();
//            String csvFileContent = body.getFileString();
//            System.out.println(csvFileContent);
//            CsvXLSXConverter csvConverter = new CsvXLSXConverter(csvFileContent);
//            String folderPath = csvConverter.generateFile();
//            long problemId = QEDProblemCreator.createProblem(problemTitle, problemDescription);
//            long snapShotId = QEDProblemCreator.getSnapshotId(problemId);
//            QEDProblemCreator.importFromMSExcel(snapShotId, folderPath);
//            return "success";
//        } catch (Exception e) {
//            return "error";
//        }
        return "hello world";
    }
}
