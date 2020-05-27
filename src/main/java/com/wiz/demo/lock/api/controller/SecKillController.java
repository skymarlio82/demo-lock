
package com.wiz.demo.lock.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiz.demo.lock.domain.mq.RabbitSender;
import com.wiz.demo.lock.domain.service.SecKillService;

@Controller
public class SecKillController {

	@Autowired
	private SecKillService secKillService = null;

	@Autowired
	private RabbitSender rabbitSender = null;

	@RequestMapping(value="/query/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String queryProductById(@PathVariable(required=true, name="id") int id) {
		return secKillService.findProductInfo(id);
	}

	@RequestMapping(value="/seckill/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String seckillByProductId(@PathVariable(required=true, name="id") int id) {
		if (secKillService.seckill(id) != 0) {
			return "Failure";
		}
		return "Success";
	}
	
	@RequestMapping(value="/sk2mq/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String seckill2mq(@PathVariable(required=true, name="id") int id) throws Exception {
		Map<String, Object> props = new HashMap<>();
		props.put("product_id", id + "_" + System.currentTimeMillis());
		rabbitSender.send(String.valueOf(id), props);
		return "Success";
	}
}