package cn.wz.goldRush.service;

import cn.wz.goldRush.entity.GoldPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

@Service
public class GoldService {

    public GoldPrice getCnBankGoldPrice() {
        GoldPrice goldPrice = new GoldPrice();
        try {
            HttpClient client = HttpClient.newHttpClient();

            // 设置请求体
            String requestBody = "{\"rateCode\": \"AUA/CNY\"}";

            // 设置请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://openapi.boc.cn/unlogin/finance/query_market_price"))
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Encoding", "gzip, deflate, br, zstd")
                    .header("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
                    .header("Acton", "")
                    .header("Authtellerno", "9881601")
                    .header("Bankno", "003")
                    .header("Branchno", "00323")
                    .header("Chnflg", "1")
                    .header("Clentid", "495")
                    .header("Clientid", "495")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Origin", "https://openapi.boc.cn")
                    .header("Referer", "https://openapi.boc.cn/erh/jljBank/EBank.html")
                    .header("Sec-Ch-Ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
                    .header("Sec-Ch-Ua-Mobile", "?0")
                    .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("Tellerno", "9881601")
                    .header("Timeout", "100000")
                    .header("Trandt", "20240522")
                    .header("Transno", "Q00001")
                    .header("Trantm", "134601")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                    .header("Userid", "")
                    .header("Uuid", "ROwFCnuTroqTnbNRnFM3K0yNluviWogK")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 发送请求
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 处理响应
            if (response.statusCode() == 200) {
                // 将JSON字符串转换为Map
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseData = objectMapper.readValue(response.body(), Map.class);

                // 获取并格式化数据
                Map<String, Object> xpadgjlInfo = (Map<String, Object>) responseData.get("xpadgjlInfo");
                goldPrice.setBid((Double) xpadgjlInfo.get("ask1"));
                goldPrice.setSell((Double)xpadgjlInfo.get("bid1"));
                goldPrice.setDate(new Date());
//                String formatted = String.format("Ask: %s, Bid: %s", xpadgjlInfo.get("ask1"), xpadgjlInfo.get("bid1"));
//                System.out.println(formatted);

            } else {
//                System.out.println("请求失败，状态码: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return goldPrice;
    }
}
