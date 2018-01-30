package com.ryx.springboot.appinterface;

import com.ryx.springboot.appinterface.domain.SystemProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppinterfaceApplication.class)
public class AppinterfaceApplicationTests {

	/** logger **/
	private static final Logger logger= LoggerFactory.getLogger(AppinterfaceApplicationTests.class);

	@Autowired
	private SystemProperties systemProperties;
	@Value("${com.ryx.desc}")
	private String testDesc;

	@Test
	public void contextLoads() {

	}

	@Test
	public void getHello() {
		logger.info(systemProperties.getAppName());
		logger.info(systemProperties.getCompany());
		logger.info(testDesc);
	}


}
