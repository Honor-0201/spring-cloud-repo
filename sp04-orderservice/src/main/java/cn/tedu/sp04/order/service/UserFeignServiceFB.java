package cn.tedu.sp04.order.service;

import org.springframework.stereotype.Component;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;

@Component
public class UserFeignServiceFB implements UserFeignService {

	@SuppressWarnings("unchecked")
	@Override
	public JsonResult<User> getUser(Integer userId) {
		// 模拟有缓存数据，向客户端发送缓存数据
		// 50%的概率发送模拟的缓存数据
		if (Math.random() < 0.4) {
			return JsonResult.ok(new User(userId, "缓存name", "缓存pwd" + userId));
		}
		return JsonResult.err("获取用户信息失败！");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public JsonResult addScore(Integer userId, Integer score) {
		return JsonResult.err("添加积分失败！");
	}

}
