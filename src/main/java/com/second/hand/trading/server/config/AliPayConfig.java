package com.second.hand.trading.server.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
	private String appId;
	private String appPrivateKey;
	private String alipayPublicKey;
	private String notifyUrl;


	@PostConstruct
	public void init() {
		// 设置参数（全局只需设置一次）
		Config config = new Config();
		config.protocol = "https";
		config.gatewayHost = "openapi.alipaydev.com";
		config.signType = "RSA2";
		config.appId = this.appId;
		config.merchantPrivateKey = this.appPrivateKey;
		config.alipayPublicKey = this.alipayPublicKey;
		config.notifyUrl = this.notifyUrl;
		Factory.setOptions(config);
		System.out.println("=======支付宝SDK初始化成功=======");
	}

	public String getAppId() {
		return appId;
	}

	public String getAppPrivateKey() {
		return appPrivateKey;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return "AliPayConfig{" +
				"appId='" + appId + '\'' +
				", appPrivateKey='" + appPrivateKey + '\'' +
				", alipayPublicKey='" + alipayPublicKey + '\'' +
				", notifyUrl='" + notifyUrl + '\'' +
				'}';
	}

}