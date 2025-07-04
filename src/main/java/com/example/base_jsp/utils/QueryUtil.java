package com.example.base_jsp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class QueryUtil {
    private QueryUtil() {}

    public static void buildQueryParams(Date fromDate, Date toDate, Map<String, Object> params) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (Objects.isNull(fromDate)) {
            params.put("fromDate", "0000-00-00");
        } else {
            params.put("fromDate", format.format(fromDate));
        }
        if (Objects.isNull(toDate)) {
            params.put("toDate", "9999-12-12");
        } else {
            params.put("toDate", format.format(toDate));
        }
    }
}
