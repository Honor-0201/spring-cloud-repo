package cn.tedu.sp04.order.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;

/*
 * 降级的类
 */
@Component
public class ItemFeignServiceFB implements ItemFeignService {

	@SuppressWarnings("unchecked")
	@Override
	public JsonResult<List<Item>> getItems(String orderId) {
		// 模拟有缓存数据，向客户端发送缓存数据
		// 50%的概率发送模拟的缓存数据
		if (Math.random() < 0.5) {
			List<Item> list = new ArrayList<Item>();
			list.add(new Item(1, "缓存商品" + 1, 1));
			list.add(new Item(2, "缓存商品" + 2, 4));
			list.add(new Item(3, "缓存商品" + 3, 9));
			list.add(new Item(4, "缓存商品" + 4, 6));
			list.add(new Item(5, "缓存商品" + 5, 5));

			return JsonResult.ok(list);
		}
		return JsonResult.err("不能获取商品列表！");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public JsonResult decreaseNumber(List<Item> items) {
		return JsonResult.err("不能减少商品库存！");
	}

}
