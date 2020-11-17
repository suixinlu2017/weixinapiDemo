package org.marker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
 


/**
 * 这个Https协议工具类，采用HttpsURLConnection实现。
 * 提供get和post两种请求静态方法
 * 
 * @author marker
 * 时间： 2014年8月30日
 * @version 1.0
 */
public class HttpUtil {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    /**
     * TrustManager
     */
    private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException { 

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException { 

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	};


	/**
	 * 发送post请求
	 * @param url url地址
	 * @param data 数据
	 * @return
	 */
	public static String sendHttpsPOST(String url, String data) {
		String result = null;

		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager},
					null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);  //设置超时时间
			HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl
					.openConnection();

            httpsConn.setConnectTimeout(5000);
            httpsConn.setReadTimeout(15000);

			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

			// 加入数据

            httpsConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpsConn.setRequestMethod("POST");
            httpsConn.setRequestProperty("accept", "*/*");
            httpsConn.setRequestProperty("connection", "Keep-Alive");
			httpsConn.addRequestProperty("Content-Type", "application/json");
			httpsConn.setDoOutput(true);
            httpsConn.setDoInput(true);
			OutputStream out = httpsConn.getOutputStream() ;
			 
			if (data != null)
				out.write(data.getBytes("UTF-8")); 
			out.flush();
			out.close();

			// 获取输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpsConn.getInputStream()));
			int code = httpsConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String temp = in.readLine();
				/* 连接成一个字符串 */
				while (temp != null) {
					if (result != null)
						result += temp;
					else
						result = temp;
					temp = in.readLine();
				}
			}
		} catch (KeyManagementException e) {
            logger.error("", e);
		} catch (NoSuchAlgorithmException e) {
            logger.error("", e);
		} catch (MalformedURLException e) {
            logger.error("", e);
		} catch (ProtocolException e) {
            logger.error("", e);
		} catch (IOException e) {
            logger.error("", e);
		}

		return result;
	}


	/**
	 * 发送get请求
	 * @param url url地址
	 * @return
	 */
	public static String sendHttpsGET(String url) {
		String result = null;

		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager },
					null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl
					.openConnection();

			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

			// 加入数据
			httpsConn.setRequestMethod("GET");
//			httpsConn.setDoOutput(true);
			  

			// 获取输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpsConn.getInputStream()));
			int code = httpsConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String temp = in.readLine();
				/* 连接成一个字符串 */
				while (temp != null) {
					if (result != null)
						result += temp;
					else
						result = temp;
					temp = in.readLine();
				}
			}
		} catch (KeyManagementException e) {
            logger.error("", e);
		} catch (NoSuchAlgorithmException e) {
            logger.error("", e);
		} catch (MalformedURLException e) {
            logger.error("", e);
		} catch (ProtocolException e) {
            logger.error("", e);
		} catch (IOException e) {
            logger.error("", e);
		}

		return result;
	}
}
