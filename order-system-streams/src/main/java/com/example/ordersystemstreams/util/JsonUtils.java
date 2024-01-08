package com.example.ordersystemstreams.util;

import com.jayway.jsonpath.JsonPath;

public class JsonUtils {

    public static String getProductId(String jsonString){
        return JsonPath.parse(jsonString).read("$.productId", String.class);
    }

    public static Integer getAmount(String jsonString){
        return JsonPath.parse(jsonString).read("$.amount", Integer.class);
    }

    public static Long getCreatedAt(String jsonString){
        return JsonPath.parse(jsonString).read("$.createdAt", Long.class);
    }

    public static String getSendingJson(String productId, Long amount){
        String jsonData = "{\"productId\":%s,\"windowedAmount\":%d,\"createdAt\":%d}";
        return String.format(jsonData, productId, amount, System.currentTimeMillis());
    }
}
