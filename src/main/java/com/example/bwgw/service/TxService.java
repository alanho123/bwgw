package com.example.bwgw.service;

import com.example.bwgw.repo.TxDataRepository;
import com.example.bwgw.vo.TxData;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TxService {
    private final TxDataRepository txDataRespository;

    public TxService(TxDataRepository txDataRespository) {
        this.txDataRespository = txDataRespository;
    }

    public TxData findByTxId(String txId) {
        return txDataRespository.findByTxId(txId);
    }

    public void save(TxData txData) {
        txDataRespository.save(txData);
    }

    public TxStatus checkTxStatus(TxData data) {
        TxStatus status = TxStatus.OK;
        if (data == null) {
            status = TxStatus.NOT_EXIST;
        } else {
            Calendar now = Calendar.getInstance();
            Calendar expire = Calendar.getInstance();
            expire.setTime(data.getExpired());
            if (expire.before(now)) {
                status = TxStatus.EXPIRED;
            }
        }
        return status;
    }

    public PaymentStatus checkPaymentStatus(TxData data) {
        PaymentStatus status;
        if (data == null) {
            status = PaymentStatus.WAIT;
        } else {
            status = data.getStatus();
        }
        return status;
    }

}
