package data.streaming.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.streaming.db.MongoUtils;
import data.streaming.dto.KeywordDTO;
import data.streaming.dto.TweetDTO;


public class Utils {
	
	
	public static final Map<String, KeywordDTO> keywordsDTO = new HashMap<String, KeywordDTO>();
	private static final ObjectMapper mapper = new ObjectMapper();
	public static final String PROPERTIES_FILE = "resources/data.properties";
	private static final int MAX_RECOMMENDATIONS = 3;

	public static TweetDTO createTweetDTO(String x) {
		TweetDTO result = null;

		try {
			result = mapper.readValue(x, TweetDTO.class);
		} catch (IOException e) {

		}
		return result;
	}

	public static KeywordDTO createKeywordDTO(TweetDTO x) {
		KeywordDTO result = null;
		String keyword = null;
		
		try {
			for(String reference: getProjectKeywords()) {
				if(reference != null && x.getText().contains(reference)) {
					keyword = reference;
				}
			}
			
			if(keyword != null) {
				if(keywordsDTO.containsKey(keyword)) {
					result = keywordsDTO.get(keyword);
					result.addOneStatisticMore();
				} else {
					result = new KeywordDTO(keyword, 1.0);
					keywordsDTO.put(keyword, result);
				}
			}			
		} catch (Exception e) {}
		
		return result;
	}
	
	
	public static List<String> getProjectKeywords() {
		return MongoUtils.getKeywords();
	}

	public static Boolean isValid(String x) {
		Boolean result = true;
		if(createTweetDTO(x) == null) {
			result = false;
		}
		return result;
	}
	
	

}
