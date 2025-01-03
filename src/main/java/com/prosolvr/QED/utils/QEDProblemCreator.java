package com.prosolvr.QED.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;

public class QEDProblemCreator {

    private static String authz = "vN4lAgEAdDcCnSBRgeIDM04GSyB1hq0FHU8Jn6tYGf7y5rrOuvwVUSQSd1GGlZBrWUTfuYETSUHY/Tr2kxulbw==";

    public static long createProblem(String problemTitle, String problemDesc) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://app.prosolvr.tech/server/problem/skeleton/upload?username=saptak.sarma01@gmail.com");
        JsonObject rqParams = new JsonObject();
        rqParams.addProperty("accountId", "145888242");
        rqParams.addProperty("appId", 565105925);
        rqParams.addProperty("problemTitle", problemTitle);
        rqParams.addProperty("problemTime", System.currentTimeMillis());
        rqParams.addProperty("problemNumber", "PROB-2023-09-11 12:31:23");
        rqParams.addProperty("problemDescription", problemDesc);
        rqParams.addProperty("problemSource", "smartQED");
        rqParams.addProperty("externalProblemId", "");
        rqParams.addProperty("externalUrl", "");
        rqParams.addProperty("priority", "UNASSIGNED");
        rqParams.addProperty("dueBy", "");
        rqParams.addProperty("problemTimezone", "Asia/Calcutta");
        httpPost.setEntity(new StringEntity(rqParams.toString()));
        httpPost.addHeader("cookie", "authz_token=" + authz);
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseBodyRaw = new String(response.getEntity().getContent().readAllBytes());
        JsonObject responseBody = JsonParser.parseString(responseBodyRaw).getAsJsonObject();
        return responseBody.get("data").getAsLong();
    }

    public static long getSnapshotId(long problemId) throws Exception {
        URI url = new URIBuilder("https://app.prosolvr.tech/server/snapshot/upload?")
                .addParameter("entityType", "FISHBONE").addParameter("problemId", String.valueOf(problemId))
                .addParameter("username", "saptak.sarma01@gmail.com").build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("cookie", "authz_token=" + authz);
        httpPost.addHeader("Content-Type", "text/html");
        httpPost.setEntity(new StringEntity(
                "<?xml-stylesheet type=\"text/css\" href=\"https://app.prosolvr.tech/css/fishbone.css\"?>"
        ));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseBodyRaw = new String(response.getEntity().getContent().readAllBytes());
        JsonObject responseBody = JsonParser.parseString(responseBodyRaw).getAsJsonObject();

        return responseBody.get("data").getAsJsonObject().get("snapshotId").getAsLong();
    }

    public static void importFromMSExcel(long problemId, String uniqueExcelPath) throws Exception {
        URI url = new URIBuilder("https://app.prosolvr.tech/server/problem/matching/importExcel")
                .addParameter("username", "saptak.sarma01@gmail.com").build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("cookie", "authz_token=" + authz);
        httpPost.addHeader("Content-Type", "application/json");
        JsonObject rqParams = new JsonObject();
        rqParams.addProperty("fishboneId", problemId);
        rqParams.addProperty("isExcelImport", "1");
        rqParams.addProperty("problemId", String.valueOf(problemId));
        rqParams.addProperty("topMatchesToConsider", 0);
        rqParams.addProperty("accountId", "145888242");
        rqParams.addProperty("userId", "717225143");
        rqParams.addProperty("file", CsvXLSXConverter.readFromExcelAndEncodeBase64(uniqueExcelPath));
        rqParams.addProperty("user_timeZone", "America/Los_Angeles");
        rqParams.addProperty("fileName", "causes.xlsx");
        httpPost.setEntity(new StringEntity(rqParams.toString()));
        httpclient.execute(httpPost);
    }
}
