package io.pivotal.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

	@Autowired
	Region<String, PdxInstance> itemRegion;
	
	@Value("${app.adyen.url}")
	private String url;
	
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

	@RequestMapping(method = RequestMethod.POST, value = "/authorize")
	public @ResponseBody String createAuthorize(@RequestBody String data) throws ClientProtocolException, IOException {
		
		String response = getResponse(url + "/authorize", data);

		PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
		
		itemRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
		
		return "POST Done.";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/capture")
	public @ResponseBody String createCapture(@RequestBody String data) throws ClientProtocolException, IOException {
		
		String response = getResponse(url + "/capture", data);

		PdxInstance pdxInstance = JSONFormatter.fromJSON(response);
		
		itemRegion.put(pdxInstance.getField("reference").toString(), pdxInstance);
		
		return "POST Done.";
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
