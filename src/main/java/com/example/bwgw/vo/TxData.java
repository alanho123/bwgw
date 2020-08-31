package com.example.bwgw.vo;

import com.example.bwgw.service.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class TxData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String channelName;
    private String orderNo;
    private Integer amount;
    private String txId;
    private PaymentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;
}
