package problems.MiniMiner;

import java.util.BitSet;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import utils.Common;

public class MiniMinerDemo {
	protected static final String PROBLEM_URL = "mini_miner/problem?access_token=942335cd269f9e0d";
	protected static final String SOLUTION_URL = "mini_miner/solve?access_token=942335cd269f9e0d";

	public static boolean checkBytes(JsonObject input, int difficulty) throws InterruptedException {
		byte[] toCheck = DigestUtils.sha256(input.toString());
		byte[] twoBytes = new byte[2];

		twoBytes[0] = toCheck[0];
		twoBytes[1] = toCheck[1];

		BitSet bitSet = BitSet.valueOf(twoBytes);

		for (int i = 0; i < difficulty; i++) {
			if (bitSet.get(i))
				return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		JsonObject jsonObject = Common.createGsonFromString(Common.httpGET(PROBLEM_URL));
		int difficulty = jsonObject.get("difficulty").getAsInt();
		JsonElement block = jsonObject.get("block");
		JsonArray data = block.getAsJsonObject().getAsJsonArray("data");

		int counter = 0;
		boolean end = false;
		while (!end) {
			System.out.println("Try #" + (counter + 1));

			JsonObject tempJsonToHash = new JsonObject();
			tempJsonToHash.add("data", data);
			tempJsonToHash.addProperty("nonce", counter);
			
			end = checkBytes(tempJsonToHash, difficulty);
			counter++;
		}

		System.out.println("Nonce value: " + (counter - 1));
		
		String payload = "{\"nonce\": " + (counter - 1) + "}";
		Common.httpPOST(SOLUTION_URL, payload);
	}
}
