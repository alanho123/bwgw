package com.example.bwgw;

import com.example.bwgw.repo.TxDataRepository;
import com.example.bwgw.vo.TxData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class GreetingController {

    private final TxDataRepository txDataRespository;

    public GreetingController(TxDataRepository txDataRespository) {
        this.txDataRespository = txDataRespository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        TxData data1 = new TxData();
        data1.setAmount(99);
        data1.setExpired(new Date());
        data1.setOrderNo("A01");
        data1.setTxId("tx001");

        TxData data2 = new TxData();
        data2.setAmount(199);
        data2.setExpired(new Date());
        data2.setOrderNo("A02");
        data2.setTxId("tx002");

        txDataRespository.save(data1);
        txDataRespository.save(data2);

        List<TxData> data = txDataRespository.findAll();
        log.info("## data - {}", data);

        return "greeting";
    }
}
