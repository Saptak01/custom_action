package com.prosolvr.QED.controllers;

import com.google.gson.JsonObject;
import com.prosolvr.QED.models.RCARequestBody;
import com.prosolvr.QED.utils.CsvXLSXConverter;
import com.prosolvr.QED.utils.QEDProblemCreator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class MainController {

    @PostMapping(path = "/getRCALink", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRCADiagramLink(HttpServletRequest req) {
        System.out.println("Executing MainController");
        
        JsonObject response = new JsonObject();  // We'll build a JSON response

        try {
            // 1. Retrieve parameters from the query string
            String problemTitle = req.getParameter("title");
            String problemDescription = req.getParameter("description");
            String encodedFileContent = req.getParameter("fileString");

            System.out.println(problemTitle);
            System.out.println(problemDescription);
            System.out.println(encodedFileContent);

            // 2. Restore '+' '/' '=' from ']' '|' '~' if your system replaced them
            String base64Restored = decode(encodedFileContent);

            // 3. Decode the Base64 to get the raw CSV
            String decodedCsv = new String(Base64.decodeBase64(base64Restored), StandardCharsets.UTF_8);

            // 4. Log the CSV for debugging
            System.out.println("[DEBUG] Decoded CSV content:\n" + decodedCsv);

            // 5. Convert CSV to XLSX
            CsvXLSXConverter csvConverter = new CsvXLSXConverter(decodedCsv);
            String folderPath = csvConverter.generateFile();

            // 6. Create the problem, get snapshot, import from Excel
            long problemId = QEDProblemCreator.createProblem(problemTitle, problemDescription);
            long snapShotId = QEDProblemCreator.getSnapshotId(problemId);
            QEDProblemCreator.importFromMSExcel(problemId, folderPath);

            // 7. Build success JSON response
            response.addProperty("url", "https://app.prosolvr.tech/#/alerts/" + problemId + "/Fishbone");
        } catch (Exception e) {
            e.printStackTrace();
            // Return a JSON object with the error message
            response.addProperty("error", e.getMessage());
        }

        // Return either {"url":"..."} on success or {"error":"..."} on failure
        return response.toString();
    }

    /**
     * This method replaces any custom placeholders for '+', '/', '='
     * back to the actual Base64 characters.
     * 
     * Example: 
     *   ']' -> '+'
     *   '|' -> '/'
     *   '~' -> '='
     */
    public String decode(String content) {
        if (content == null) {
            return "";
        }
        char[] contentCharArray = content.toCharArray();
        for (int i = 0; i < contentCharArray.length; i++) {
            if (contentCharArray[i] == ' ') {
                contentCharArray[i] = '+';
            } else if (contentCharArray[i] == '|') {
                contentCharArray[i] = '/';
            } else if (contentCharArray[i] == '~') {
                contentCharArray[i] = '=';
            }
        }
        return new String(contentCharArray);
    }
}


// package com.prosolvr.QED.controllers;

// import com.google.gson.JsonObject;
// import com.prosolvr.QED.models.RCARequestBody;
// import com.prosolvr.QED.utils.CsvXLSXConverter;
// import com.prosolvr.QED.utils.QEDProblemCreator;
// import jakarta.servlet.http.HttpServletRequest;
// import org.apache.commons.codec.binary.Base64;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class MainController {

//     @PostMapping(path = "/getRCALink", produces = MediaType.APPLICATION_JSON_VALUE)
//     public String getRCADiagramLink(HttpServletRequest req) {
//         System.out.println("Executing MainController");
//         try {
//             String problemTitle = req.getParameter("title");
//             System.out.println(problemTitle);
//             String problemDescription = req.getParameter("description");
//             System.out.println(problemDescription);
//             String encodedFileContent = req.getParameter("fileString");
//             System.out.println(encodedFileContent);
//             String decodedFile = new String(Base64.decodeBase64(decode(encodedFileContent)));
//             System.out.println(decodedFile);
//             CsvXLSXConverter csvConverter = new CsvXLSXConverter(decodedFile);
//             String folderPath = csvConverter.generateFile();
//             long problemId = QEDProblemCreator.createProblem(problemTitle, problemDescription);
//             long snapShotId = QEDProblemCreator.getSnapshotId(problemId);
//             QEDProblemCreator.importFromMSExcel(problemId, folderPath);
//             JsonObject response = new JsonObject();
//             response.addProperty("url", "https://app.prosolvr.tech/#/alerts/" + problemId + "/Fishbone");
//             return response.toString();
//         } catch (Exception e) {
//             e.printStackTrace();
//             return e.getMessage();
//         }
//     }

//     public String decode(String content) {
//         char[] contentCharArray = content.toCharArray();
//         for(int i = 0; i < contentCharArray.length; i++) {
//             if(contentCharArray[i] == ']') {
//                 contentCharArray[i] = '+';
//             } else if(contentCharArray[i] == '|') {
//                 contentCharArray[i] = '/';
//             } else if(contentCharArray[i] == '~') {
//                 contentCharArray[i] = '=';
//             }
//         }

//         return new String(contentCharArray);
//     }
// }

