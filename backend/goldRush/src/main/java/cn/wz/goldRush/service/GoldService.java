package cn.wz.goldRush.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoldService {

    private void addHeaders(PostMethod method){
        method.addRequestHeader("Sec-Ch-Ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"");
        method.addRequestHeader("Sec-Ch-Ua-Mobile", "?0");
        method.addRequestHeader("Sec-Ch-Ua-Platform", "Windows");
        method.addRequestHeader("Sec-Fetch-Dest", "empty");
        method.addRequestHeader("Sec-Fetch-Mode", "cors");
        method.addRequestHeader("Sec-Fetch-Site", "same-origin");
        method.addRequestHeader("", "");
        method.addRequestHeader("Uuid", "ROwFCnuTroqTnbNRnFM3K0yNluviWogK");
        method.addRequestHeader("Userid", "");
        method.addRequestHeader("Tellerno", "9881601");
        method.addRequestHeader("Timeout", "100000");
        method.addRequestHeader("Trandt", "20240522");
        method.addRequestHeader("Transno", "Q00001");
        method.addRequestHeader("Trantm", "134601");
        method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
        method.addRequestHeader("Branchno", "00323");
        method.addRequestHeader("Chnflg", "1");
        method.addRequestHeader("Clentid", "495");
        method.addRequestHeader("Clientid", "495");
        method.addRequestHeader("Connection", "keep-alive");
        method.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        method.addRequestHeader("Host", "openapi.boc.cn");
        method.addRequestHeader("Origin", "https://openapi.boc.cn");
        method.addRequestHeader("Referer", "https://openapi.boc.cn/erh/jljBank/EBank.html");
        method.addRequestHeader("Bankno", "003");
        method.addRequestHeader("Authtellerno", "9881601");
        method.addRequestHeader("Accept",  "application/json, text/plain, */*");
    }

    public void getCnBankGoldPrice() {
        try {
            String url = "https://openapi.boc.cn/unlogin/finance/query_market_price";
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);
            method.addParameter("rateCode", "AUA/CNY");
            addHeaders(method);
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
//            logger.error("Method failed: " + method.getStatusLine());
            }
            byte[] responseBody = method.getResponseBody();
            String responseStr = new String(responseBody,"UTF-8");
            HashMap hashMap = new ObjectMapper().readValue(responseStr, HashMap.class);
            System.out.println(hashMap.toString());
        }catch (IOException ioException){}
    }
}
