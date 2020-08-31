package com.example.bwgw.util;

import com.github.f4b6a3.uuid.UuidCreator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

    public static String getTxId() {
        String[] s = UuidCreator.getPrefixComb().toString().split("-");
        StringBuilder sb = new StringBuilder(sdf.format(new Date()));
        sb.append("-")
                .append(s[1])
                .append("-")
                .append(s[2])
                .append("-")
                .append(s[3])
                .append("-")
                .append(s[4]);

        return sb.toString();
    }

}
