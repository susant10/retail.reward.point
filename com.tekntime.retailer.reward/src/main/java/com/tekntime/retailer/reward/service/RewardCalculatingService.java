package com.tekntime.retailer.reward.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tekntime.retailer.reward.model.Reward;
import com.tekntime.retailer.reward.model.Transcation;


@Service
public class RewardCalculatingService {
	private static final Logger logger   = LoggerFactory.getLogger(RewardCalculatingService.class);
	

	private List<Transcation> fetchTxns() {
		List<Transcation> transactions=new ArrayList<>();
		transactions.add(mock01());
		transactions.add(mock02());
		transactions.add(mock11());
		transactions.add(mock12());
		transactions.add(mock21());
		return transactions;
	}

   	private Map<String, List<Transcation>> splitTxnByMonth(List<Transcation> transactions) {
		return transactions.stream().collect(Collectors.groupingBy(transaction -> transaction.getDate().getYear()+""+transaction.getDate().getMonth()));
	}

    public  List<Reward> calculate()  {
    	List<Transcation> transactions= fetchTxns();
    	Map<String, List<Transcation>> mapTxnByMonth=splitTxnByMonth(transactions);
    	List<Reward> rewards=new ArrayList<>();
    	mapTxnByMonth.entrySet().forEach(e->{
    		Reward reward=new Reward();
    		reward.setMonthYear(e.getKey());
    		reward.setTransactions(e.getValue());
    		reward.calculate();
    		rewards.add(reward);
    	});
    	return rewards;
    }

    private Date dealDate(String YYYYMMDD){
    	try {
	    	DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	    	return format.parse(YYYYMMDD);
    	}catch(ParseException pe) {
    		logger.error("Unable to parse date {}", YYYYMMDD);
    		return null;
    	}
    }
    private Transcation mock01() {
    
    	Transcation txn1=new Transcation();
    	txn1.setAmount(120);
    	txn1.setDate(dealDate("20230225"));
    	return txn1;
    }
    
    private Transcation mock02() {
        
    	Transcation txn1=new Transcation();
    	txn1.setAmount(80);
    	txn1.setDate(dealDate("20230226"));
    	return txn1;
    }
    
    private Transcation mock11() {
    	Transcation txn1=new Transcation();
    	txn1.setAmount(60);
    	txn1.setDate(dealDate("20230310"));
    	return txn1;
    }
    private Transcation mock12() {
    	Transcation txn1=new Transcation();
    	txn1.setAmount(40);
    	txn1.setDate(dealDate("20230311"));
    	return txn1;
    }
    
    private Transcation mock21() {
    	Transcation txn1=new Transcation();
    	txn1.setAmount(140);
    	txn1.setDate(dealDate("20230401"));
    	return txn1;
    }
    
}
