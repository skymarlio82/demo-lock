
package com.wiz.demo.lock.domain.schedule;

import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wiz.demo.lock.common.constant.AppConstant;
import com.wiz.demo.lock.domain.service.SecKillService;

@Component
//@EnableAsync
public class SeckillProcessJob {

	@Autowired
	private SecKillService secKillService = null;

//	@Async
    @Scheduled(fixedDelay=1000)
	public void processSeckillReqs() throws Exception {
		System.out.println("====>>>> REQ_CACHE_QUEUE [" + AppConstant.REQ_CACHE_QUEUE + "]");
//		List<FutureTask<Integer>> ts = new ArrayList<>();
//		if (AppConstant.REQ_CACHE_QUEUE.size() != 0) {
//			ExecutorService executorService = Executors.newFixedThreadPool(10);
//			for (int i = 0; i < 10; i++) {
//				Integer productId = AppConstant.REQ_CACHE_QUEUE.poll();
//				if (productId != null) {
//					Callable<Integer> callable = () -> secKillService.seckill(productId);
//					FutureTask<Integer> futureTask = new FutureTask<>(callable);
//					ts.add(futureTask);
//					executorService.submit(futureTask);
//				}
//			}
//			for (FutureTask<Integer> t : ts) {
//				int res = t.get();
//				if (res != 0) {
//					AppConstant.REQ_CACHE_QUEUE.add(res);
//				}
//			}
//			executorService.shutdown();
//		}
		List<Integer> pool = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Integer productId = AppConstant.REQ_CACHE_QUEUE.poll();
			if (productId != null) {
				pool.add(productId);
			}
		}
		for (int productId : pool) {
			secKillService.seckill(productId);
		}
	}
}