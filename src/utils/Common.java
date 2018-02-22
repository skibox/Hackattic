package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Common {
	public static final String URL_START = "https://hackattic.com/challenges/";

	public static void httpPOST(String url_end, String payload) throws Exception {
		String URL = URL_START + url_end;
		// String URL2 = "https://requestb.in/14hk4tr1";

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(URL);

		StringEntity postString = new StringEntity(payload);
		post.setEntity(postString);
		post.setHeader("Content-type", "application/json");

		System.out.println("post");
		System.out.println(post.getEntity().getContent().getClass().getName());
		showBytes(post.getEntity().getContent());

		HttpResponse response = httpClient.execute(post);

		System.out.println("response");
		System.out.println(response.getEntity().getContent().getClass().getName());
		System.out.println(response.getStatusLine());
		showBytes(response.getEntity().getContent());

	}

	public static String httpGET(String url_end) throws Exception {
		String URL = URL_START + url_end;
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(URL);

		HttpResponse response = httpClient.execute(get);
		// System.out.println("GET response code: " +
		// response.getStatusLine().getStatusCode());

		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder output = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
			output.append(line);
		}

		return output.toString();
	}

	public static void showBytes(InputStream is) throws Exception {
		int size = is.available();
		char[] chars = new char[size];
		byte[] bytes = new byte[size];

		is.read(bytes, 0, size);
		for (int i = 0; i < size; i++) {
			chars[i] = (char) (bytes[i] & 0xff);
		}

		System.out.println(new String(chars));

	}
	
	public static String createStringGsonFromString(String input) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(input).getAsJsonObject();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);
	
		return prettyJson;
	}
	
	public static JsonObject createGsonFromString(String input) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(input).getAsJsonObject();
		
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return json;
	}
}
