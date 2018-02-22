package problems.PasswordHashing;

public class Scrypt {
	private int N;
	private int r;
	private int p;
	private int buflen;
	private String _control;
	
	protected int getN() {
		return N;
	}

	protected int getR() {
		return r;
	}

	protected int getP() {
		return p;
	}

	protected int getBuflen() {
		return buflen;
	}

	protected String get_control() {
		return _control;
	}

	@Override
	public String toString() {
		return "Scrypt [N=" + N + ", r=" + r + ", p=" + p + ", buflen=" + buflen + ", _control=" + _control + "]";
	}
	
}

