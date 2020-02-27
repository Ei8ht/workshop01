package com.icd.wksh.commons;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    public static Map<String, Object> fail(String msg) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(Constant.RESULT, null);
        response.put(Constant.STATUS, Constant.ERROR);
        response.put(Constant.MESSAGE, msg);
        response.put(Constant.TIMESTAMP, getCurrentGmtMillis());
        return response;
    }

    public static Map<String, Object> success(Object responseObj) {
        return success(responseObj, "Success");
    }

    public static Map<String, Object> success(Object responseObj, String msg) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(Constant.RESULT, responseObj);
        response.put(Constant.STATUS, Constant.SUCCESS);
        response.put(Constant.MESSAGE, msg);
        response.put(Constant.TIMESTAMP, getCurrentGmtMillis());
        return response;
    }

    public static long getCurrentGmtMillis() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
