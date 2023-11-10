package com.tekntime.retailer.reward.model;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class Transaction { 
	private String customerid;
	private Date date;
	private double amount;
	
	public double point() {
		if(amount >=50 && amount <=100) {
			return (amount -50) *1;
		}else if(amount > 100) {
			return (amount -50) *1 +(amount-100)*1;
		}else {
			return 0;
		}
	}
}

