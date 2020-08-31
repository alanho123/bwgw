package com.example.bwgw.http;

import lombok.Data;

@Data
public class OrderInfo {
    private String channelName;
    private String orderNo;
    private Integer amount;
}
