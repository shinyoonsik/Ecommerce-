package com.example.ordersystemstreams.service;

import com.example.ordersystemstreams.util.JsonUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@JsonSerialize
public class EventRuleService {
    private static final long WINDOW_SIZE = 20000L; // 20초
    private static final int CPU_USAGE_LIMIT = 80;
    private static final Map<String, Integer> countMap = new HashMap<>(); // key: productId, value = count
    private static final Map<String, Timestamp> timeStampMap = new HashMap<>(); // key: productId, value = timestamp(윈도우의 시작점을 셋팅)

    public void isOverLimitCount(String json) {
        if(json == null) return;

        String productId = JsonUtils.getProductId(json);
        Integer amount = JsonUtils.getAmount(json);

        // 초기화
        if(!countMap.containsKey(productId)){
            Integer count = 0;
            countMap.put(productId, count);
        }
        // 초기화
        if(!timeStampMap.containsKey(productId)){
            Timestamp initTimestamp = new Timestamp(System.currentTimeMillis());
            Timestamp stdTime = new Timestamp(initTimestamp.getTime() + WINDOW_SIZE);
            timeStampMap.put(productId, stdTime);
        }

        Timestamp windowStdTime = timeStampMap.get(productId);
        Timestamp currentTime = new Timestamp(JsonUtils.getCreatedAt(json));
        if((currentTime.after(windowStdTime))){ // true: 새로운 윈도우를 의미함
            System.out.println("Window사이즈를 넘었으므로 key별 count를 0으로 업데이트");
            Integer newCount = 0;
            Timestamp newStdTime = new Timestamp(currentTime.getTime() + WINDOW_SIZE);

            countMap.put(productId, newCount);
            timeStampMap.put(productId, newStdTime);
        }

        if (amount >= CPU_USAGE_LIMIT) {
            Integer count = countMap.get(productId) + 1;
            countMap.put(productId, count);

            System.out.println("after -> current count: " + count);
        }else{
            System.out.println("조건을 통과하지 못한 count 상태: " + countMap.get(productId));
        }
    }

    public boolean isThreeTimesAbove80(String key) {
        String productId = key;
        Integer count = countMap.get(productId);

        if(count == null) return false;

        return count >= 3;
    }
}
