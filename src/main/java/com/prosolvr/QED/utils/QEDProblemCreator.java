package com.prosolvr.QED.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base32;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;

public class QEDProblemCreator {

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
        httpPost.addHeader("cookie", "authz_token=Bs57Hv+9VzgMPcuZM4Jvwcz5RN9YwQMjXdwUirSlcmFOVXPjS+VuUNZYVPdrTE0QBvLh+BHs9T+Ks4tEMNeAMA==");
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseBodyRaw = new String(response.getEntity().getContent().readAllBytes());
        JsonObject responseBody = JsonParser.parseString(responseBodyRaw).getAsJsonObject();
        return responseBody.get("data").getAsLong();
    }

    public static long getSnapshotId(long problemId) throws Exception {
//        Base32 b32 = new Base32();
        URI url = new URIBuilder("https://app.prosolvr.tech/server/snapshot/upload?")
                .addParameter("entityType", "FISHBONE").addParameter("problemId", String.valueOf(problemId))
                .addParameter("username", "gasgasgasjr@gmail.com").build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("cookie", "authz_token=Bs57Hv+9VzgMPcuZM4Jvwcz5RN9YwQMjXdwUirSlcmFOVXPjS+VuUNZYVPdrTE0QBvLh+BHs9T+Ks4tEMNeAMA==");
        httpPost.addHeader("Content-Type", "text/html");
        httpPost.setEntity(new StringEntity(
                "<?xml-stylesheet type=\"text/css\" href=\"https://app.prosolvr.tech/css/fishbone.css\"?>"
        ));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseBodyRaw = new String(response.getEntity().getContent().readAllBytes());
        JsonObject responseBody = JsonParser.parseString(responseBodyRaw).getAsJsonObject();

        return responseBody.get("data").getAsJsonObject().get("snapshotId").getAsLong();
    }

    public static void importFromMSExcel(long problemId, String uniqueExcelPath) throws Exception{
        URI url = new URIBuilder("https://app.prosolvr.tech/server/problem/matching/importExcel")
                .addParameter("username", "gasgasgasjr@gmail.com").build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("cookie", "authz_token=Bs57Hv+9VzgMPcuZM4Jvwcz5RN9YwQMjXdwUirSlcmFOVXPjS+VuUNZYVPdrTE0QBvLh+BHs9T+Ks4tEMNeAMA==");
        httpPost.addHeader("Content-Type", "application/json");
        JsonObject rqParams = new JsonObject();
        rqParams.addProperty("fishboneId", problemId);
        rqParams.addProperty("isExcelImport", 1);
        rqParams.addProperty("problemId", problemId);
        rqParams.addProperty("topMatchesToConsider", 0);
        rqParams.addProperty("accountId", "782841846");
        rqParams.addProperty("userId", "782841846");
//        rqParams.addProperty("appId", 760998598);
        rqParams.addProperty("file", CsvXLSXConverter.readFromExcelAndEncodeBase64(uniqueExcelPath));
        rqParams.addProperty("problemTimezone", "Asia/Calcutta");
        rqParams.addProperty("fileName", "causes.xlsx");
        httpPost.setEntity(new StringEntity(rqParams.toString()));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        System.out.println("test");
        System.out.println(response.getStatusLine().toString());
    }
}
