package com.example.bwgw;

import com.example.bwgw.http.*;
import com.example.bwgw.service.PaymentStatus;
import com.example.bwgw.service.TxService;
import com.example.bwgw.service.TxStatus;
import com.example.bwgw.util.IdUtil;
import com.example.bwgw.util.QRCodeUtil;
import com.example.bwgw.vo.TxData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;

@RestController
@Slf4j
public class PaymentController {

    private final TxService txService;

    public PaymentController(TxService txService) {
        this.txService = txService;
    }

    //查詢交易結果
    @PostMapping("/result")
    public Result result(@RequestBody TxInfo txInfo) {
        log.info("### get payment result - {}", txInfo.getTxId());

        TxData data = txService.findByTxId(txInfo.getTxId());
        PaymentStatus status = txService.checkPaymentStatus(data);
        Result result = new Result();

        switch (status) {
            case OK:
                result.setCode("0000");
                result.setUrl("http://localhost:8080/result.html");
                break;
            case WAIT:
                result.setCode("0099");
                break;
        }
        return result;
    }

    //測試用，完成交易
    @PostMapping("/finish")
    public OrderData finish(@RequestBody TxInfo txInfo) {
        log.info("## transaction finished, tx_id - {}", txInfo.getTxId());
        OrderData orderData = new OrderData();
        TxData data = txService.findByTxId(txInfo.getTxId());

        log.info("## finish data - {}", data);

        if (data != null) {
            data.setStatus(PaymentStatus.OK);
            txService.save(data);
            orderData.setAmount(data.getAmount());
            orderData.setOrderNo(data.getOrderNo());
            orderData.setChannelName(data.getChannelName());
            orderData.setTxId(txInfo.getTxId());
        }
        return orderData;
    }

    //QRCode掃碼頁
    @RequestMapping(value = "/barcode")
    public ModelAndView home(@RequestParam("tx_id") String txId, Model model) throws IOException {
        TxData data = txService.findByTxId(txId);
        TxStatus txStatus = txService.checkTxStatus(data);
        ModelAndView view = new ModelAndView("default");
        switch (txStatus) {
            case OK:
                view = new ModelAndView("index");
                model.addAttribute("imageUrl", "/image/" + txId);
                model.addAttribute("txId", txId);
                break;
            case EXPIRED:
                view = new ModelAndView("expired");
                break;
            case NOT_EXIST:
                view = new ModelAndView("nodata");
                break;
        }
        return view;
    }

    //產生QRCode
    @RequestMapping(value = "/image/{tx_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("tx_id") String txId) throws Exception {
        log.info("## create QRCode - {}", txId);
        byte[] imageContent = QRCodeUtil.generateQRCode(txId);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

//    @GetMapping(value = "/qrcode}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<BufferedImage> qrgenQRCode() throws Exception {
//        return okResponse(QRCodeUtil.generateQRCodeImage("hello"));
//    }

//    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
//        return new ResponseEntity<>(image, HttpStatus.OK);
//    }


    //BreezePay付款
    @PostMapping("/breezePay")
    public PayInfo breezePay(@RequestBody OrderInfo orderInfo) {
        log.info("## order info - {}", orderInfo);

        String txId = IdUtil.getTxId();
        saveOrderData(orderInfo, txId);

        PayInfo payInfo = new PayInfo("http://localhost:8888/barcode?tx_id=" + txId, txId, orderInfo.getOrderNo());

        return payInfo;
    }

    //儲存交易資料
    private void saveOrderData(OrderInfo orderInfo, String txId) {
        //1分鐘內有效
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 1);
        TxData data = new TxData();
        data.setChannelName(orderInfo.getChannelName());
        data.setAmount(orderInfo.getAmount());
        data.setOrderNo(orderInfo.getOrderNo());
        data.setTxId(txId);
        data.setExpired(now.getTime());
        data.setStatus(PaymentStatus.WAIT);
        txService.save(data);

        log.info("## saved successfully - {}", data);
    }

}
