package cn.tony.goldpricepushnotification.service;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import cn.tony.goldpricepushnotification.entity.GoldPrice;

public class GoldService {
    public GoldPrice getCnBankGoldPrice() {
        GoldPrice goldPrice = new GoldPrice();
        try {
            // 创建 URL 对象
            URL url = new URL("https://openapi.boc.cn/unlogin/finance/query_market_price");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            // 设置请求方法
            connection.setRequestMethod("POST");

            // 设置请求头
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br, zstd");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
            connection.setRequestProperty("Acton", "");
            connection.setRequestProperty("Authtellerno", "9881601");
            connection.setRequestProperty("Bankno", "003");
            connection.setRequestProperty("Branchno", "00323");
            connection.setRequestProperty("Chnflg", "1");
            connection.setRequestProperty("Clentid", "495");
            connection.setRequestProperty("Clientid", "495");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Origin", "https://openapi.boc.cn");
            connection.setRequestProperty("Referer", "https://openapi.boc.cn/erh/jljBank/EBank.html");
            connection.setRequestProperty("Sec-Ch-Ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"");
            connection.setRequestProperty("Sec-Ch-Ua-Mobile", "?0");
            connection.setRequestProperty("Sec-Ch-Ua-Platform", "\"Windows\"");
            connection.setRequestProperty("Sec-Fetch-Dest", "empty");
            connection.setRequestProperty("Sec-Fetch-Mode", "cors");
            connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
            connection.setRequestProperty("Tellerno", "9881601");
            connection.setRequestProperty("Timeout", "100000");
            connection.setRequestProperty("Trandt", "20240522");
            connection.setRequestProperty("Transno", "Q00001");
            connection.setRequestProperty("Trantm", "134601");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
            connection.setRequestProperty("Userid", "");
            connection.setRequestProperty("Uuid", "ROwFCnuTroqTnbNRnFM3K0yNluviWogK");

            // 发送 POST 请求
            connection.setDoOutput(true);
            String requestBody = "{\"rateCode\": \"AUA/CNY\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 处理响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    // 将 JSON 字符串转换为 Map
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseData = objectMapper.readValue(response.toString(), Map.class);

                    // 获取并格式化数据
                    Map<String, Object> xpadgjlInfo = (Map<String, Object>) responseData.get("xpadgjlInfo");

                    // 获取 ask 和 bid 值，并处理类型转换
                    Number askNumber = (Number) xpadgjlInfo.get("ask1");
                    Number bidNumber = (Number) xpadgjlInfo.get("bid1");

                    // 将 ask 和 bid 转换为 Double 类型
                    Double ask = askNumber instanceof Integer ? askNumber.doubleValue() : (Double) askNumber;
                    Double bid = bidNumber instanceof Integer ? bidNumber.doubleValue() : (Double) bidNumber;

                    // 设置 goldPrice 对象的属性
                    goldPrice.setBid(ask);
                    goldPrice.setSell(bid);
                    goldPrice.setterUpdateDate(new Date());
                }
            } else {
                System.out.println("请求失败，状态码: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goldPrice;
    }
}
