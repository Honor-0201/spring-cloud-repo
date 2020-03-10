package cn.tedu.sp11;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import cn.tedu.web.util.JsonResult;
import io.micrometer.core.instrument.util.StringUtils;

@Component
public class AccessFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// 判断当前请求是否应用当前过滤器
		// 如果请求后台item-service 执行过滤器
		String serviceId = (String) RequestContext.getCurrentContext().get(FilterConstants.SERVICE_ID_KEY);
		if (serviceId.equals("item-service")) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		// 过滤代码
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String token = request.getParameter("token");
		if (StringUtils.isEmpty(token)) {
			// 阻止继续执行，
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JsonResult.err("没有登陆数据").toString());

		}
		// zuul设计时，考虑拓展，添加了返回值，至今为止仍未使用
		return null;
	}

	@Override
	public String filterType() {
		// 返回过滤器类型：前置 后置 路由 错误
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		// 过滤器顺序号
		/*
		 * 在第5个过滤器中，在context对象里添加了serviceId 这样在后面过滤器才可以获取serviceId
		 */
//		return 6;
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

}
