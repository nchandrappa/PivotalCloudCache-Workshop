package io.pivotal.pad.workshop.paymentservices.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.pad.workshop.paymentservices.domain.TransactionRules;
import io.pivotal.pad.workshop.paymentservices.repo.TransactionRulesRepo;

@RestController
public class ReferenceDataContorller {
	
	@Autowired
	TransactionRulesRepo transactionRulesRepo;
	
	@RequestMapping(method = RequestMethod.GET, value = "/load", 
			produces = "application/json")
	@ResponseBody
	public String loadDB() throws Exception {

		generateData();
		return "Reference Data successfully saved.";
	}

	private void generateData() {
		
		Random r = new Random();
		TransactionRules transactionRule;
		Integer id = 1;
		for(String stateCode: listOfUSStatesCode) {
			
			double rangeMin = 0.0;
			double rangeMax = 9.0;
			double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			
			transactionRule = new TransactionRules(id, stateCode, randomValue);
			transactionRulesRepo.save(transactionRule);
			id = id + 1;
		}	
	}

	@SuppressWarnings("serial")
    public final static Map<String, String> mapOfUSStates = new HashMap<String, String>() {
        {
            put("AL", "Alabama");
            put("AK", "Alaska");
            put("AZ", "Arizona");
            put("AR", "Arkansas");
            put("CA", "California");
            put("CO", "Colorado");
            put("CT", "Connecticut");
            put("DE", "Delaware");
            put("DC", "Dist of Columbia");
            put("FL", "Florida");
            put("GA", "Georgia");
            put("HI", "Hawaii");
            put("ID", "Idaho");
            put("IL", "Illinois");
            put("IN", "Indiana");
            put("IA", "Iowa");
            put("KS", "Kansas");
            put("KY", "Kentucky");
            put("LA", "Louisiana");
            put("ME", "Maine");
            put("MD", "Maryland");
            put("MA", "Massachusetts");
            put("MI", "Michigan");
            put("MN", "Minnesota");
            put("MS", "Mississippi");
            put("MO", "Missouri");
            put("MT", "Montana");
            put("NE", "Nebraska");
            put("NV", "Nevada");
            put("NH", "New Hampshire");
            put("NJ", "New Jersey");
            put("NM", "New Mexico");
            put("NY", "New York");
            put("NC", "North Carolina");
            put("ND", "North Dakota");
            put("OH", "Ohio");
            put("OK", "Oklahoma");
            put("OR", "Oregon");
            put("PA", "Pennsylvania");
            put("RI", "Rhode Island");
            put("SC", "South Carolina");
            put("SD", "South Dakota");
            put("TN", "Tennessee");
            put("TX", "Texas");
            put("UT", "Utah");
            put("VT", "Vermont");
            put("VA", "Virginia");
            put("WA", "Washington");
            put("WV", "West Virginia");
            put("WI", "Wisconsin");
            put("WY", "Wyoming");
        }
    };
    
    public final static List<String> listOfUSStatesCode = new ArrayList<String>(mapOfUSStates.keySet());

}
