/*
* Demo for Nacos
* pom.xml
    <dependency>
        <groupId>com.alibaba.nacos</groupId>
        <artifactId>nacos-client</artifactId>
        <version>${version}</version>
    </dependency>
*/
package org.halk.nacos;

import java.util.Properties;
import java.util.concurrent.Executor;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * Config service example
 *
 * @author Nacos
 *
 */
public class ConfigExample {

	public static void main(String[] args) throws NacosException, InterruptedException {
		String serverAddr = "localhost";
		String dataId = "mogu-admin-dev.yaml";
		String group = "dev";
		String namespace = "dev";
		Properties properties = new Properties();
		properties.put("serverAddr", serverAddr);
		properties.put("namespace", namespace);
		ConfigService configService = NacosFactory.createConfigService(properties);
		String content = configService.getConfig(dataId, group, 5000);
		System.out.println(content);
		configService.addListener(dataId, group, new Listener() {
			@Override
			public void receiveConfigInfo(String configInfo) {
				System.out.println("recieve:" + configInfo);
			}

			@Override
			public Executor getExecutor() {
				return null;
			}
		});

		for (;;){}

//		boolean isPublishOk = configService.publishConfig(dataId, group, "content");
//		System.out.println(isPublishOk);
//
//		Thread.sleep(3000);
//		content = configService.getConfig(dataId, group, 5000);
//		System.out.println(content);

//		boolean isRemoveOk = configService.removeConfig(dataId, group);
//		System.out.println(isRemoveOk);
//		Thread.sleep(3000);

//		content = configService.getConfig(dataId, group, 5000);
//		System.out.println(content);
//		Thread.sleep(300000);

	}
}
