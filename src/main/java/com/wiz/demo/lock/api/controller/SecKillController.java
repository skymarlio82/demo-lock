package com.wiz.demo.lock.api.controller;

import com.wiz.demo.lock.domain.mq.RabbitSender;
import com.wiz.demo.lock.domain.service.SecKillService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SecKillController {
    private final SecKillService secKillService;
    private final RabbitSender rabbitSender;

    public SecKillController(SecKillService secKillService, RabbitSender rabbitSender) {
        this.secKillService = secKillService;
        this.rabbitSender = rabbitSender;
    }

    @GetMapping("/query/{id}")
    @ResponseBody
    public String queryProductById(@PathVariable(required = true, name = "id") int id) {
        return secKillService.findProductInfo(id);
    }

    @GetMapping("/seckill/{id}")
    @ResponseBody
    public String seckillByProductId(@PathVariable(required = true, name = "id") int id) {
        if (secKillService.seckill(id) != 0) {
            return "Failure";
        }
        return "Success";
    }

    @GetMapping("/sk2mq/{id}")
    @ResponseBody
    public String seckill2mq(@PathVariable(required = true, name = "id") int id) {
        Map<String, Object> props = new HashMap<>();
        props.put("product_id", id + "_" + System.currentTimeMillis());
        try {
            rabbitSender.send(String.valueOf(id), props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success";
    }
}