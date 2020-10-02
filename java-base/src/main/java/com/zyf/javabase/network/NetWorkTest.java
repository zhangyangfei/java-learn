package com.zyf.javabase.network;

import java.io.IOException;
import java.net.InetAddress;

import org.springframework.stereotype.Service;

/**
 * 网络编程
 */
@Service
public class NetWorkTest {

    //ip对象测试
	public void inetAddressTest() throws IOException {
		InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
		System.out.println("是否可以访问：" + inetAddress.isReachable(2000));
		System.out.println("ip：" + inetAddress.getHostAddress());
		
		InetAddress localAddress= InetAddress.getByAddress(new byte[] {127,0,0,1});
		System.out.println("是否可以访问：" + localAddress.isReachable(2000));
		System.out.println("ip：" + localAddress.getHostAddress());
	}
}
