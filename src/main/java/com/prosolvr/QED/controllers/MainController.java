package com.prosolvr.QED.controllers;

import com.google.gson.JsonObject;
import com.prosolvr.QED.models.RCARequestBody;
import com.prosolvr.QED.utils.CsvXLSXConverter;
import com.prosolvr.QED.utils.QEDProblemCreator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping(path = "/getRCALink", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRCADiagramLink(HttpServletRequest req) {
        System.out.println("Executing MainController");
        try {
            String problemTitle = req.getParameter("title");
            String problemDescription = req.getParameter("description");
            String encodedFileContent = req.getParameter("fileString");
            System.out.println(encodedFileContent);
            String decodedFile = new String(Base64.decodeBase64(decode(encodedFileContent)));
            System.out.println(decodedFile);
            CsvXLSXConverter csvConverter = new CsvXLSXConverter(decodedFile);
            String folderPath = csvConverter.generateFile();
            long problemId = QEDProblemCreator.createProblem(problemTitle, problemDescription);
            long snapShotId = QEDProblemCreator.getSnapshotId(problemId);
            QEDProblemCreator.importFromMSExcel(problemId, folderPath);
            JsonObject response = new JsonObject();
            response.addProperty("url", "https://app.prosolvr.tech/#/alerts/" + problemId + "/Fishbone");
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.printStackTrace();
        }
    }

    public String decode(String content) {
        char[] contentCharArray = content.toCharArray();
        for(int i = 0; i < contentCharArray.length; i++) {
            if(contentCharArray[i] == ']') {
                contentCharArray[i] = '+';
            } else if(contentCharArray[i] == '|') {
                contentCharArray[i] = '/';
            } else if(contentCharArray[i] == '~') {
                contentCharArray[i] = '=';
            }
        }

        return new String(contentCharArray);
    }
}
