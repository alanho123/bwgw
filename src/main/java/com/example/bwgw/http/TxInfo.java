package com.example.bwgw.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TxInfo {
    private String txId;
    private String orderNo;
}
