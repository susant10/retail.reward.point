package com.tekntime.retailer.reward.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.retailer.reward.model.Reward;
import com.tekntime.retailer.reward.model.view.TotalReward;
import com.tekntime.retailer.reward.service.RewardCalculatingService;

@RestController
@RequestMapping("/reward")
public class RewardController {
	Logger logger = LoggerFactory.getLogger(RewardController.class);
	
	
	@Autowired
	private RewardCalculatingService service;

	
	@GetMapping("/point")
    public ResponseEntity<?> calculate() throws Exception {
		logger.info("reached basic-auth app" );
		Map<String, String> responsemap = new HashMap<String, String>();
		if(responsemap.isEmpty()) {
			List<TotalReward> rewards= service.calculate(responsemap);
			
			return ResponseEntity.ok(rewards);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsemap);
		}
        
    }
	
	@GetMapping("/point/{id}")
    public ResponseEntity<?> calculate(@PathVariable("id") String customerId) throws Exception {
		logger.info("reached basic-auth app" );
		Map<String, String> map = new HashMap<String, String>();
		TotalReward total= service.calculate(customerId);
		
		return ResponseEntity.ok(total);
        
    }


}
