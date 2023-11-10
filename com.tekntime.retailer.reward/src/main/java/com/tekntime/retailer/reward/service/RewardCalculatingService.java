package com.tekntime.retailer.reward.service;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.tekntime.retailer.reward.model.Reward;
import com.tekntime.retailer.reward.model.Transaction;
import com.tekntime.retailer.reward.model.view.TotalReward;


@Service
public class RewardCalculatingService {
	private static final Logger logger   = LoggerFactory.getLogger(RewardCalculatingService.class);
	
	@Autowired
	private ApplicationContext ctx;

	
	
	private  List<Transaction> loadDataset() throws Exception{  
		List<Transaction> transactions=new ArrayList<>();
		Resource resource = ctx.getResource("classpath:dataset.csv");
		File csvFile = resource.getFile();
		
        try (CSVReader csvReader  = new CSVReader(new FileReader(csvFile))) {
        	int line=0;
            String[] value= null;
            while ((value = csvReader.readNext()) != null) {
            	if(line ==0) {
            		line++;
            		continue;
            	}
            	Transaction txn=new Transaction();
            	txn.setCustomerid(value[0]);
            	txn.setDate(dealDate(value[1]));
            	txn.setAmount(Double.parseDouble(value[2]));
            	transactions.add(txn);
            }
            return transactions;
        } catch (Exception e) {
        	logger.error("error loading csv file", e);
        	throw e;
        }
	}
	
	private List<Transaction> fetchTxns() throws Exception {
		List<Transaction> transactions=loadDataset();
		return transactions;
	}

   	private Map<String, List<Transaction>> splitTxnByMonth(List<Transaction> transactions) {
		return transactions.stream().collect(Collectors.groupingBy(transaction -> monthYear(transaction.getDate())));
	}

    public List<TotalReward> calculate(Map<String, String> responsemap) throws Exception  {
    	Map<String, List<Transaction>> mapTxnByCustomer = fetchTxns().stream().collect(Collectors.groupingBy(transaction -> transaction.getCustomerid()));
    	
    	List<TotalReward> rewardsByCustomer=new ArrayList<>();
    	mapTxnByCustomer.entrySet().forEach(e->{
    		try {
    			rewardsByCustomer.add(calculate(e.getKey()));
    		}catch(Exception ex) {
    			responsemap.put("520", "Interal Serverv error");
    		}
    	});
    	return rewardsByCustomer;
    }
    
    public  TotalReward calculate(String customerid) throws Exception  {
    	List<Transaction> transactions= fetchTxns().stream().filter(e->e.getCustomerid().equalsIgnoreCase(customerid)).collect(Collectors.toList());
    	Map<String, List<Transaction>> mapTxnByMonth=splitTxnByMonth(transactions);
    	List<Reward> rewards=new ArrayList<>();
    	mapTxnByMonth.entrySet().forEach(e->{
    		Reward reward=new Reward();
    		reward.setMonthYear(e.getKey());
    		reward.setTransactions(e.getValue());
    		reward.calculate();
    		rewards.add(reward);
    	});
    	
    	TotalReward total=new TotalReward();
		total.setRewards(rewards);
		total.point();
		
    	return total;
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

    private String monthYear(Date date){
    	try {
	    	DateFormat format = new SimpleDateFormat("yyyyMM", Locale.ENGLISH);
	    	return format.format(date);
    	}catch(Exception pe) {
    		logger.error("Unable to parse date {}", date);
    		return null;
    	}
    }

}
