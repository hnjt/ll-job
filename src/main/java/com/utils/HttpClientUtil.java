package com.utils;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient 工具类 直接调用doRequest请求返回JSONString结果
 * by ChenYb data 2019-05-14
 */
public class HttpClientUtil {
    private static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    private static String doGet(String url) {
        return doGet(url, null);
    }
    private static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    private static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 工具类入口
     * @param requestStr
     * @param ipStr
     * @param protStr
     * @param methodStr
     * @param param
     * @return JSONString
     */
    public static String doRequest (String requestStr, String ipStr,String protStr,String methodStr ,Map<String ,String> param){
        String result = "";
        StringBuffer url = new StringBuffer("http://").append(ipStr).append(":").append(protStr).append(methodStr);
        if (StringUtils.isEmpty(requestStr)||StringUtils.isBlank(requestStr)){
            requestStr = "GET";
        }
        if ("GET".equals(requestStr.toUpperCase())){
            if (null != param)
                result = doGet(url.toString(), param);
            else
                result = doGet(url.toString());
        }
        if ("POST".equals(requestStr.toUpperCase())){
            if (null != param)
                result = doPost(url.toString(), param);
            else
                result = doPost(url.toString());
        }
        return result;
    }

//    public static void main(String[] args) {
//        Map<String,String> map = new HashMap<>();
//        map.put("参数1","var1");
//        map.put("参数2","var2");
//        String post = HttpClientUtil.doRequest("请求方式(get,post)", "ip", "端口", "方法路径", map);
//        System.out.println(post);
//    }
}