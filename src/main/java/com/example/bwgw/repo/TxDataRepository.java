package com.example.bwgw.repo;

import com.example.bwgw.vo.TxData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxDataRepository extends JpaRepository<TxData, Long> {

    TxData findByTxId(String txId);
}
