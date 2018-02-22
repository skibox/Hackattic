package problems.PasswordHashing;

public class PasswordHash {
	private String password;
	private String salt;
	private Pbkdf2 pbkdf2;
	private Scrypt scrypt;
	
	@Override
	public String toString() {
		return "PasswordHash [password=" + password + ", salt=" + salt + ", pbkdf2=" + pbkdf2 + ", scrypt=" + scrypt
				+ "]";
	}

	protected String getPassword() {
		return password;
	}

	protected String getSalt() {
		return salt;
	}

	protected Pbkdf2 getPbkdf2() {
		return pbkdf2;
	}

	protected Scrypt getScrypt() {
		return scrypt;
	}
	

}

