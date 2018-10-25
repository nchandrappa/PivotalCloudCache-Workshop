package io.pivotal.pad.workshop.paymentservices.service;

import java.io.IOException;

import org.apache.geode.cache.Region;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
	
	private Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Value("${app.adyen.url}")
	private String url;
	
	@Autowired
	Region<String, PdxInstance> transactionsRegion;
	
	
	public boolean authorizeTransaction(String transactionData) {
		
		String response;
		boolean authorizeSuccess = false;
		try {
			response = getResponse(url + "/authorize", transactionData);
			PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
			transactionsRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
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
			System.out.println(response);
			PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
			transactionsRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
			captureSuccess = true;
		} catch (IOException e) {
			logger.error("Exception during transaction capture - " + e.getLocalizedMessage());
			return captureSuccess;
		}
		return captureSuccess;
	}
	
	
	private String getResponse(String endpoint, String data) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		StringEntity entity = new StringEntity(data);
		HttpPost httppost = new HttpPost(endpoint);
		httppost.setEntity(entity);
		httppost.setHeader("content-type", "application/json");
		
		HttpResponse response = httpClient.execute(httppost);
		HttpEntity responseEntity = response.getEntity();
		
		String content = EntityUtils.toString(responseEntity);
		
		return content;
	}

}
