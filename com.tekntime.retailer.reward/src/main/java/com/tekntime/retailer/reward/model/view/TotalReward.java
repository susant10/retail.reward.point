package com.tekntime.retailer.reward.model.view;

import java.util.List;

import com.tekntime.retailer.reward.model.Reward;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class TotalReward { 
	private List<Reward> rewards;
	private double totalPoint;
	
	public void point() {
		totalPoint =rewards.stream().mapToDouble(Reward::getMonthlyPoint).sum();
	}
}

