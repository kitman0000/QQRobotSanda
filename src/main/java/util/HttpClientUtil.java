/**
 * 2019年2月19日
 */
package util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {
	/**
	 * 发送http get请求
	 *
	 * @param url
	 * @param headers
	 * @param encode
	 * @return HttpResponse
	 */
	public static String httpGet(final String url, final Map<String, String> headers, final String encode) {
		// final HttpResponse response = new HttpResponse();
		// if (encode == null) {
		// encode = "utf-8";
		// }
		String content = null;
		// since 4.3 不再使用 DefaultHttpClient
		final CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		final HttpGet httpGet = new HttpGet(url);
		// 设置header
		if (headers != null && headers.size() > 0) {
			for (final Map.Entry<String, String> entry : headers.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = closeableHttpClient.execute(httpGet);
			final HttpEntity entity = httpResponse.getEntity();
			content = EntityUtils.toString(entity, encode);
			// response.setBody(content);
			// response.setHeaders(httpResponse.getAllHeaders());
			// response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
			// response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		try { // 关闭连接、释放资源
			closeableHttpClient.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 发送 http post 请求，参数以原生字符串进行提交
	 *
	 * @param url
	 * @param stringJson
	 *            原生字符串
	 * @param headers
	 * @param encode
	 * @return HttpResponse
	 */
	public static String httpPostRaw(final String url, final String stringJson, final Map<String, String> headers, String encode) {
		// final HttpResponse response = new HttpResponse();
		if (encode == null) {
			encode = "utf-8";
		}

		System.out.println(stringJson);

		// HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
		final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		final HttpPost httpost = new HttpPost(url);
		// 设置header
		httpost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		httpost.setHeader("Host", "localhost:8000");
		if (headers != null && headers.size() > 0) {
			for (final Map.Entry<String, String> entry : headers.entrySet()) {
				httpost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		// 组织请求参数
		final StringEntity stringEntity = new StringEntity(stringJson, encode);
		httpost.setEntity(stringEntity);
		String content = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 响应信息
			httpResponse = closeableHttpClient.execute(httpost);
			final HttpEntity entity = httpResponse.getEntity();
			content = EntityUtils.toString(entity, encode);
			// response.setBody(content);
			// response.setHeaders(httpResponse.getAllHeaders());
			// response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
			// response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		try { // 关闭连接、释放资源
			closeableHttpClient.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
