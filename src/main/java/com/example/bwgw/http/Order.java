package com.example.bwgw.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @JsonProperty("tx_id")
    private String txId="";

    @JsonProperty("amount")
    private Integer amount=0;

    @JsonProperty("order_no")
    private String orderNo="";

    @JsonProperty("channel_name")
    private String channelName="";

    @JsonProperty("tx_type")
    private String txType="";
}
