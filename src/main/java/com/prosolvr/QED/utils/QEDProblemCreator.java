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

    private static String authz = "mI89EF4+zkA+LPpL/Wm2YLrjVCjRCCteMRH2V2fpMAUlwkukG5pb2ia7srCAHoL85ekRHDFGGRJPXQj0mOqsOQ==";

    public static long createProblem(String problemTitle, String problemDesc) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://app.prosolvr.tech/server/problem/skeleton/upload?username=gasgasgasjr@gmail.com");
        JsonObject rqParams = new JsonObject();
        rqParams.addProperty("accountId", "782841846");
        rqParams.addProperty("appId", 760998598);
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
                .addParameter("username", "gasgasgasjr@gmail.com").build();
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
                .addParameter("username", "gasgasgasjr@gmail.com").build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("cookie", "authz_token=" + authz);
        httpPost.addHeader("Content-Type", "application/json");
        JsonObject rqParams = new JsonObject();
        rqParams.addProperty("fishboneId", problemId);
        rqParams.addProperty("boundary", "");
        rqParams.addProperty("nodeId", "");
        rqParams.addProperty("status_legend", "");
        rqParams.addProperty("color_legend", "");
        rqParams.addProperty("isExcelImport", 1);
        rqParams.addProperty("problemId", String.valueOf(problemId));
        rqParams.addProperty("topMatchesToConsider", 0);
        rqParams.addProperty("accountId", "782841846");
        rqParams.addProperty("userId", "142131478");
        rqParams.addProperty("file", CsvXLSXConverter.readFromExcelAndEncodeBase64(uniqueExcelPath));
        rqParams.addProperty("user_timeZone", "America/Los_Angeles");
        rqParams.addProperty("fileName", "causes.xlsx");
        StringEntity bodyEntity = new StringEntity(rqParams.toString());
        System.out.println(bodyEntity);
        System.out.println(rqParams.toString());
        httpPost.setEntity(new StringEntity(rqParams.toString()));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        System.out.println(response.getStatusLine().toString());
    }
}
