package cn.tedu.sp04.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserFeignService userFeignService;

	@Autowired
	private ItemFeignService itemFeignService;

	@Override
	public Order getOrder(String orderId) {
		// TODO: 调用user-service获取用户信息
		JsonResult<User> user = userFeignService.getUser(7);// 用户id从redis中获取
		// TODO: 调用item-service获取商品信息
		JsonResult<List<Item>> items = itemFeignService.getItems(orderId);
		Order order = new Order();
		order.setId(orderId);
		order.setUser(user.getData());
		order.setItems(items.getData());
		return order;
	}

	@Override
	public void addOrder(Order order) {
		// TODO: 调用item-service减少商品库存
		itemFeignService.decreaseNumber(order.getItems());
		// TODO: 调用user-service增加用户积分
		userFeignService.addScore(order.getUser().getId(), 1000);
		log.info("保存订单：" + order);
	}

}
