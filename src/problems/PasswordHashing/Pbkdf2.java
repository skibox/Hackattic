package problems.PasswordHashing;

public class Pbkdf2 {
	private int rounds;
	private String hash;
	
	protected int getRounds() {
		return rounds;
	}

	protected String getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return "Pbkdf2 [rounds=" + rounds + ", hash=" + hash + "]";
	}
	
}
