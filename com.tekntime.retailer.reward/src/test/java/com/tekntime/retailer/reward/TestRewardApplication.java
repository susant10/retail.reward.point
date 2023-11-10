package com.tekntime.retailer.reward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestRewardApplication {
	private static final Logger logger   = LoggerFactory.getLogger(RewardApplication.class);
	
	public static void main(String[] args) {
		 SpringApplication.run(RewardApplication.class);
	}
	
}
