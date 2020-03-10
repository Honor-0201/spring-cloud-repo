package cn.tedu.sp11;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemServiceFallBack implements FallbackProvider {

	@Override
	public String getRoute() {
		/**
		 * 当访问后台的哪个服务，应用当前的降级代码
		 */
		log.info("ItemServiceFallBack执行了");
		return "item-service";
		/*
		 * 所有服务应用当前降级类 return "*"; return null;
		 * 
		 */
	}

	/**
	 * 方法返回的response对象，封装向客户端返回的响应数据
	 */
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return response();

	}

	private ClientHttpResponse response() {
		return new ClientHttpResponse() {

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}

			@Override
			public InputStream getBody() throws IOException {
				String json = JsonResult.err("后台商品服务出错！").toString();
				return new ByteArrayInputStream(json.getBytes("UTF-8"));
			}

			@Override // ok
			public String getStatusText() throws IOException {
				return HttpStatus.OK.getReasonPhrase();
			}

			@Override // 200,ok
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}

			@Override // 200
			public int getRawStatusCode() throws IOException {
				return HttpStatus.OK.value();
			}

			@Override
			public void close() {
			}
		};
	}

}
