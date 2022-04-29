package com.mooc.meetingfilm.backendshowconsumer.ribbon;

import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 获取serverList组件的三种方法：①、写死的；②、动态修改的；③、和Eureka Service结合的
 * @author wang
 * @create 2022-04-26
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException, ClientException, InterruptedException {

        //方法一：写死的，即读配置文件
        ConfigurationManager.loadPropertiesFromResources("jiangzh.properties");
        System.err.println(ConfigurationManager.getConfigInstance().getProperty("jiangzh-client.ribbon.listOfServers"));

        // 构建一个HttpClient，serverList固定的情况
        RestClient client = (RestClient) ClientFactory.getNamedClient("jiangzh-client");  // 2
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build(); // 3

        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request); // 4
            System.err.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }

        //方法二、动态修改serverList
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.err.println(lb.getLoadBalancerStats());
        ConfigurationManager.getConfigInstance().setProperty(
                "jiangzh-client.ribbon.listOfServers", "www.qq.com:80,www.taobao.com:80"); // 5
        System.err.println("changing servers ...");

        Thread.sleep(3000); // 6

        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.err.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
        }
        System.out.println(lb.getLoadBalancerStats()); // 7

        //方法三、最好的情况就是Ribbon和注册中心结合使用
    }


}
