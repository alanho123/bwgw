package com.example.bwgw.http;

import lombok.Data;

@Data
public class OrderData {
    private String channelName;
    private String orderNo;
    private Integer amount;
    private String txId;
}
