package com.icd.wksh.commons;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Util {
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof List) {
            List<?> resultObj = (List<?>) obj;
            return resultObj.isEmpty();
        } else if (obj.getClass() == Integer.class) {
            int resultObj = (int) obj;
            return resultObj == 0;
        } else if (obj.getClass() == Double.class) {
            double resultObj = (double) obj;
            return resultObj == 0;
        } else if (obj instanceof BigDecimal) {
            BigDecimal resultObj = (BigDecimal) obj;
            return resultObj == null;
        } else if (obj instanceof String) {
            String resultObj = (String) obj;
            return "".equals(resultObj);
        } else if (obj instanceof Integer[]) {
            Integer[] resultObj = (Integer[]) obj;
            return resultObj.length == 0;
        } else if (obj instanceof int[]) {
            int[] resultObj = (int[]) obj;
            return resultObj.length == 0;
        } else if (obj instanceof String[]) {
            String[] resultObj = (String[]) obj;
            return resultObj.length == 0;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static <T> Optional<T> wrap(T object) {
        if(!isEmpty(object)){
            return Optional.of(object);
        } else {
            return Optional.empty();
        }
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        Date out = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return out;
    }

}
