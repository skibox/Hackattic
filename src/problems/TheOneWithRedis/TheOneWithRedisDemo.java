package problems.TheOneWithRedis;

import java.util.Base64;

import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;
import utils.Common;

public class TheOneWithRedisDemo {
	public static final String PROBLEM_URL 	= "the_redis_one/problem?access_token=942335cd269f9e0d";
	public static final String SOLUTION_URL = "the_redis_one/solve?access_token=942335cd269f9e0d";
	
	public static void main(String[] args) throws Exception {
		String input = Common.httpGET(PROBLEM_URL);
		JsonObject inputJson = Common.createGsonFromString(input);
		
		String rdb = inputJson.get("rdb").toString();
		rdb = rdb.replaceAll("\"", "");
		byte[] decodedBytes = Base64.getDecoder().decode(rdb);
		String decodedString = new String(decodedBytes, "UTF-8");
		
		System.out.println(decodedString);
		System.out.println();
		System.out.println(inputJson);
		Jedis jedis = new Jedis();
		
		
	}
}
