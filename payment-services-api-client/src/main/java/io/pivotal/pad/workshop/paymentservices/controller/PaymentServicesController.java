package io.pivotal.pad.workshop.paymentservices.controller;

import org.apache.geode.cache.Region;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.pad.workshop.paymentservices.service.TransactionService;

@RestController
public class PaymentServicesController {
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	Region<String, PdxInstance> itemRegion;
	
	@RequestMapping(method = RequestMethod.POST, value = "/authorize")
	public @ResponseBody String createAuthorize(@RequestBody String data) {
			
		boolean transactionAutorize = transactionService.authorizeTransaction(data);
		
		if (transactionAutorize) {
			return "Transaction was capture Succesfully.";
		}
		return "Transaction Capture Failure.";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/capture")
	public @ResponseBody String createCapture(@RequestBody String data) {
		
		boolean transactionCapture = transactionService.captureTransaction(data);
		
		if (transactionCapture) {
			return "Transaction was capture Succesfully.";
		}
		return "Transaction Capture Failure.";	
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/show")
	@ResponseBody
	public String show() throws Exception {
		StringBuilder result = new StringBuilder();
		
		itemRegion.values().forEach(item->result.append(JSONFormatter.toJSON(item)+"<br/>"));

		return result.toString();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/clear")
	@ResponseBody
	public String clearCache() throws Exception {
		itemRegion.removeAll(itemRegion.keySetOnServer());
		return "Region cleared";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(value = "reference", required = true) String id) {

		PdxInstance result = itemRegion.get(id);

		return JSONFormatter.toJSON(result);
	}
	
}
