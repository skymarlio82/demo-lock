
package com.wiz.demo.lock.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wiz.demo.lock.common.model.RedisLock;
import com.wiz.demo.lock.data.dao.OrderDao;
import com.wiz.demo.lock.data.dao.ProductDao;
import com.wiz.demo.lock.data.entity.Order;
import com.wiz.demo.lock.data.entity.Product;

@Service
public class SecKillService {

	@Autowired
	private ProductDao productDao = null;

	@Autowired
	private OrderDao orderDao = null;

	@Autowired
	private RedisLock redisLock = null;

	@Transactional(readOnly=true)
	public String findProductInfo(int productId) {
		String result = "";
		Product product = productDao.findById(productId);
		List<Order> orders = orderDao.findByProductName(product.getName());
		int numConsumed = orders.stream().mapToInt(o -> o.getAmount()).sum();
		result = "商品: " + product.getName() + " [10000], 目前剩余" + product.getStock() + "; 该商品成功下单数量: " + numConsumed;
		return result;
	}

	@Transactional
	public int seckill(int productId) {
		long time = System.currentTimeMillis() + 1000*5;
		String key = "upt_stk_lck_prd_" + productId;
		// 如果加锁失败
		if (!redisLock.tryLock(key, String.valueOf(time))) {
			return productId;
		}
		Product product = productDao.findById(productId);
		Order order = new Order(0, product.getName(), 1, new Date());
		orderDao.add(order);
		product.setStock(product.getStock() - 1);
		productDao.update(product);
		// 解锁
		redisLock.unlock(key, String.valueOf(time));
		return 0;
	}

	@Transactional
	public void seckill1(int productId) {
		Product product = productDao.findById(productId);
		Order order = new Order(0, product.getName(), 1, new Date());
		orderDao.add(order);
		product.setStock(product.getStock() - 1);
		productDao.update(product);
	}
}