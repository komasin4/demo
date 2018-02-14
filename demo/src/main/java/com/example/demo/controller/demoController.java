package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Result;
import com.example.demo.model.ResultModel;
import com.google.gson.Gson;

@Controller
public class demoController {
	
	private static final Logger logger = LoggerFactory.getLogger(demoController.class);
	@Autowired com.example.demo.service.demoService demoService;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody String test()	{
		System.out.println("debug test 1");
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
		System.out.println("debug test 2");
		return "test!";
	}
	
	@PostMapping(value="/parse")
	public @ResponseBody Object parsingTest(@RequestParam String url, @RequestParam String tag)	{
		
		
		try {
			Document doc = Jsoup.connect(url).get();
			logger.info(doc.title());
			/*
			Elements newsHeadlines = doc.select("#mp-itn b a");
			for (Element headline : newsHeadlines) {
				logger.info("%s\n\t%s", 
			    headline.attr("title"), headline.absUrl("href"));
			}
			*/
			
			Elements elements = doc.getElementsMatchingText(tag);
			
			logger.info(doc.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		String result = new String();
		StringBuffer sbResult = new StringBuffer();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		try {
			HttpResponse response = client.execute(request);

			System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());
			
			HttpEntity resEntity = response.getEntity();
			
			if (resEntity != null) 
				result = EntityUtils.toString(resEntity,"utf-8");
			
			System.out.println(result);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return "parsing test";
	}
	
	@RequestMapping(value="/request/item/{item_no}", method=RequestMethod.GET)	
		public @ResponseBody Object requestItem(@PathVariable String item_no)	{

		if(item_no == null || item_no.isEmpty())
			item_no = "000660";
		
		String query = "SERVICE_ITEM:" + item_no;
		
		return demoService.requestDetail(query);
		
	}

	@RequestMapping(value="/request/index/{index}", method=RequestMethod.GET)	
	public @ResponseBody Object requestIndexem(@PathVariable String index)	{

	if(index == null || index.isEmpty())
		index = "KOSPI";
	
	String query = "SERVICE_INDEX:" + index.toUpperCase();
	
	return demoService.requestDetail(query);
	
	}

}
