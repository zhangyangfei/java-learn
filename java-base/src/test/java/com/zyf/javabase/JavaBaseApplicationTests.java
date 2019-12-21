package com.zyf.javabase;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zyf.javabase.network.NetWorkTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaBaseApplicationTests {

	@Autowired
	NetWorkTest netWorkTest;

	@Test
	public void test1() {
		try {
			netWorkTest.inetAddressTest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
