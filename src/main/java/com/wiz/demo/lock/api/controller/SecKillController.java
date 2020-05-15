
package com.wiz.demo.lock.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiz.demo.lock.common.constant.AppConstant;
import com.wiz.demo.lock.domain.service.SecKillService;

@Controller
public class SecKillController {

	@Autowired
	private SecKillService secKillService = null;

	@RequestMapping(value="/query/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String queryProductById(@PathVariable(required=true, name="id") int id) {
		return secKillService.findProductInfo(id);
	}

	@RequestMapping(value="/seckill/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String seckillByProductId(@PathVariable(required=true, name="id") int id) {
		AppConstant.REQ_CACHE_QUEUE.add(id);
//		if (!secKillService.seckill(id).equals("Success")) {
//			return "系统繁忙 ...";
//		}
//		return secKillService.findProductInfo(id);
//		return secKillService.seckill(id);
		return "Success";
	}
}