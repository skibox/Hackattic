package problems.PasswordHashing;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.generators.SCrypt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.Common;

public class PasswordHashingDemo {
	protected static final String PROBLEM_URL 	= "password_hashing/problem?access_token=942335cd269f9e0d";
	protected static final String SOLUTION_URL = "password_hashing/solve?access_token=942335cd269f9e0d";

	protected static byte[] decodeSalt(String salt) throws Exception {
		return Base64.getDecoder().decode(salt);
	}

	protected static String getSaltedPassword(PasswordHash gson) throws Exception {
		StringBuilder saltedPassword = new StringBuilder();
		saltedPassword.append(gson.getPassword()).append(decodeSalt(gson.getSalt()));

		return saltedPassword.toString();
	}

	protected static String calculateSHA256(PasswordHash gson) throws Exception {
		String textToHash = gson.getPassword();
		return DigestUtils.sha256Hex(textToHash);
	}

	protected static String calculateHMAC(PasswordHash gson) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(decodeSalt(gson.getSalt()), "HmacSHA256");
		sha256_HMAC.init(secretKey);

		String textToHash = gson.getPassword();

		return Hex.encodeHexString(sha256_HMAC.doFinal(textToHash.getBytes("UTF-8")));
	}

	protected static String calculatePBKDF2(PasswordHash gson) throws Exception {
		KeySpec spec = new PBEKeySpec(gson.getPassword().toCharArray(), decodeSalt(gson.getSalt()),
				gson.getPbkdf2().getRounds(), 256);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

		return Hex.encodeHexString(factory.generateSecret(spec).getEncoded());
	}

	protected static String calculateScrypt(PasswordHash gson) throws Exception {
		byte[] password = gson.getPassword().getBytes("UTF-8");
		byte[] salt = decodeSalt(gson.getSalt());
		int costParam = gson.getScrypt().getN();
		int parallelization = gson.getScrypt().getP();
		int blockSize = gson.getScrypt().getR();
		int buflen = gson.getScrypt().getBuflen();

		byte[] output = SCrypt.generate(password, salt, costParam, blockSize, parallelization, buflen);
		return Hex.encodeHexString(output);
	}

	protected static String generateJSON(PasswordHash gson) throws Exception {
		Gson gsonOut = new GsonBuilder().create();
		String sha = calculateSHA256(gson);
		String hmac = calculateHMAC(gson);
		String pbdkf2 = calculatePBKDF2(gson);
		String scrypt = calculateScrypt(gson);

		return gsonOut.toJson(new OutputJSON(sha, hmac, pbdkf2, scrypt));
	}
	
	protected static PasswordHash generateGSON() throws Exception {
		Gson gson = new GsonBuilder().create();
		PasswordHash passwordHash = gson.fromJson(Common.httpGET(PROBLEM_URL), PasswordHash.class);

		return passwordHash;
	}
	
	protected static void main(String[] args) throws Exception{
		PasswordHash gson = generateGSON();
		System.out.println("password- " + gson.getPassword());
		System.out.println("salt- " + gson.getSalt());
		System.out.println("decodedSalt- " + decodeSalt(gson.getSalt()));
		System.out.println("saltedpassword - " + (gson.getPassword() + decodeSalt(gson.getSalt())));

		Common.httpPOST(SOLUTION_URL, generateJSON(gson));
	}
}
