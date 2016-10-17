package http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	private CookieStore cookieStore;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}

	public void cookiesHttpTry(String url, File outFile, int tryCount) {
		for (int i = 0; i < tryCount; i++) {
			try {
				System.out.println("send request time:"+new Date());
				cookiesHttp(url, outFile);
				return;
			} catch (Exception ex) {
				outFile.delete();
				System.out.println("send request url:"+url);
				ex.printStackTrace();
			}
		}
		throw new RuntimeException("超過重試次數:"+url);
	}

	public void cookiesHttp(String url, File outFile) throws IOException {
		HttpGet httpget = new HttpGet(url);
		// 設定config，例如timeout的時間，1000是1秒的意思吧
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setSocketTimeout(1000)
				.setConnectTimeout(100000).build();
		httpget.setConfig(requestConfig);

		try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
				CloseableHttpResponse response = httpclient.execute(httpget)) {
			System.out.println("build ok execute ok");
			// CloseableHttpResponse response = httpclient.execute(httpget, context);

			HttpEntity entity = response.getEntity();
			System.out.println("entity=" + entity);
			// InputStream in = entity.getContent();
			if (entity != null) {
				try (InputStream in = entity.getContent(); FileOutputStream fo = new FileOutputStream(outFile)) {
					IOUtils.copy(in, fo);
					System.out.println("write file ok==" + outFile);
				}
				// IOUtils.copy(input, output);
				// byte[] bs = IOUtils.toByteArray(in);
				// System.out.println("length=" + bs.length);
				// FileUtils.writeByteArrayToFile(outFile, bs);
				// System.out.println("write file ok==" + outFile);
			}
			EntityUtils.consume(entity);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 由chrome導出的cookies來建立cookieStore
	 * @param cookieStr
	 */
	public void setCookieStore(String cookieStr) {
		CookieStore cookieStore = new BasicCookieStore();
		for (String s : cookieStr.split("},")) {
			if (!s.endsWith("}")) {
				s += "}";
			}
			JSONObject json = JSONObject.fromObject(s);
			BasicClientCookie bc = new BasicClientCookie(json.getString("name"), json.getString("value"));
			bc.setDomain(json.getString("domain"));
			bc.setPath(json.getString("path"));
			bc.setExpiryDate(DateUtils.addDays(new Date(), 1));
			bc.setSecure(json.getBoolean("secure"));
			bc.setAttribute(ClientCookie.PATH_ATTR, bc.getPath());
			bc.setAttribute(ClientCookie.DOMAIN_ATTR, bc.getDomain());
			cookieStore.addCookie(bc);
			this.cookieStore = cookieStore;
		}

	}

	public static String getHttp(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			// System.out.println("Executing request " + httpget.getRequestLine());
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity, "utf8") : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}

}
