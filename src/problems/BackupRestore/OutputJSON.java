package problems.BackupRestore;

import java.util.Vector;

public class OutputJSON {
	private Vector<String> alive_ssns;
	
	public OutputJSON(Vector<String> ssn_list) {
		setAlive_ssns(ssn_list);
	}

	public Vector<String> getAlive_ssns() {
		return alive_ssns;
	}

	public void setAlive_ssns(Vector<String> alive_ssns) {
		this.alive_ssns = alive_ssns;
	}
}
