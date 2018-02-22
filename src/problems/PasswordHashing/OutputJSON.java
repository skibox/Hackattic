package problems.PasswordHashing;

public class OutputJSON {
	private String sha256;
	private String hmac;
	private String pbkdf2;
	private String scrypt;
	
	protected OutputJSON(String sha, String hmac, String pbdkf2, String scrypt) {
		setSha256(sha);
		setHmac(hmac);
		setPbkdf2(pbdkf2);
		setScrypt(scrypt);
	}
	
	protected String getSha256() {
		return sha256;
	}
	protected void setSha256(String sha256) {
		this.sha256 = sha256;
	}
	protected String getHmac() {
		return hmac;
	}
	protected void setHmac(String hmac) {
		this.hmac = hmac;
	}
	protected String getPbkdf2() {
		return pbkdf2;
	}
	protected void setPbkdf2(String pbkdf2) {
		this.pbkdf2 = pbkdf2;
	}
	protected String getScrypt() {
		return scrypt;
	}
	protected void setScrypt(String scrypt) {
		this.scrypt = scrypt;
	}
}

