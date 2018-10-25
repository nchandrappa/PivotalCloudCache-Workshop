## Implement Payment Services API Client

PCC provides native json support and we'll configure the Payment Services API app for to insert json documents into PCC Region.

#### Step 1: Update the CloudCacheConfig.java to create Client Region used for storing transaction records. 

Note: Client Region Name should match the Region Created using GFSH on PCC Servers

```
@Bean(name = "transactionsRegion")
@DependsOn({"gemfireCache"})
public Region<String, PdxInstance> transactionsRegion(@Autowired ClientCache clientCache) {
	ClientRegionFactory<String, PdxInstance> transactionsRegionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
	Region<String, PdxInstance> transactionsRegion = transactionsRegionFactory.create("Transactions");
	return transactionsRegion;
}
```

#### Step 2: Implement TransactionService.java for Authorizing and Capturing the transactions. Refer - io.pivotal.pad.workshop.paymentservices.service.TransactionService

```
public boolean authorizeTransaction(String transactionData) {
		
	String response;
	boolean authorizeSuccess = false;
	try {
		response = getResponse(url + "/authorize", transactionData);
		PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
		itemRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
		authorizeSuccess = true;
	} catch (IOException e) {
		logger.error("Exception during transaction authorize - " + e.getLocalizedMessage());
		return authorizeSuccess;
	}
	return authorizeSuccess;
}

public boolean captureTransaction(String transactionData) {
	
	String response;
	boolean captureSuccess = false;
	try {
		response = getResponse(url + "/capture", transactionData);
		PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
		itemRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
		captureSuccess = true;
	} catch (IOException e) {
		logger.error("Exception during transaction capture - " + e.getLocalizedMessage());
		return captureSuccess;
	}
	return captureSuccess;
}
```

#### Step 3: Implement Payment Services API /authorize and /capture in PaymentServicesController.java

```
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
```

#### Step 4: Update the app.adyen.url property in application.properties to mock response generator deployed here - https://authorization-capture-api-silly-genet.apps.pcfone.io