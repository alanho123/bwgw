package com.example.bwgw;

import com.example.bwgw.http.Order;
import com.example.bwgw.http.ResponseData;
import com.example.bwgw.http.TxInfo;
import com.example.bwgw.service.TxService;
import com.example.bwgw.service.TxStatus;
import com.example.bwgw.vo.TxData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@Slf4j
public class OrderController {

    private final TxService txService;

    public OrderController(TxService txService) {
        this.txService = txService;
    }

    //查詢交易資料
    @PostMapping("/txInfo")
    public ResponseData txInfo(@RequestBody TxInfo txInfo) {
        Calendar now = Calendar.getInstance();

        ResponseData responseData = new ResponseData();
        responseData.setCode("9999");
        Order order = new Order();
        responseData.setOrder(order);

        TxData data = txService.findByTxId(txInfo.getTxId());
        TxStatus txStatus = txService.checkTxStatus(data);
        log.info("## data - {}", data);

        switch (txStatus) {
            case OK:
                order.setAmount(data.getAmount());
                order.setChannelName(data.getChannelName());
                order.setOrderNo(data.getOrderNo());
                order.setTxId(data.getTxId());
                order.setTxType("WEB");
                responseData.setCode("0000");
                break;
            case EXPIRED:
                responseData.setCode("1110");
                break;
            case NOT_EXIST:
                responseData.setCode("1100");
                break;
        }
        return responseData;
    }


}
