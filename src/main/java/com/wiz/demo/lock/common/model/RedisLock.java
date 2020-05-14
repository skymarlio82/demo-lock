
package com.wiz.demo.lock.common.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public Boolean tryLock(String key, String value) {
		// 这个其实就是setnx命令，只不过在java这边稍有变化，返回的是boolean
		if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}
		// 避免死锁，且只让一个线程拿到锁
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		// 如果锁过期了
		if (!StringUtils.isEmpty(currentValue) && Long.valueOf(currentValue) < System.currentTimeMillis()) {
			// 获取上一个锁的时间 如果高并发的情况可能会出现已经被修改的问题 所以多一次判断保证线程的安全
			String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			// 只会让一个线程拿到锁，如果旧的value和currentValue相等，只会有一个线程达成条件，因为第二个线程拿到的oldValue已经和currentValue不一样了
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}
		return false;
	}

	public void unlock(String key, String value) {
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
			stringRedisTemplate.opsForValue().getOperations().delete(key);
		}
	}
}