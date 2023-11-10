package com.tekntime.retailer.reward.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class Reward { 
	private String monthYear;
	private List<Transcation> transactions;
	private double point;

	
	public void calculate() {
		point =transactions.stream().mapToDouble(Transcation::point).sum();
	}
	
}

