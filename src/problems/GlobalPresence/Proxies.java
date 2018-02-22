package problems.GlobalPresence;

public class Proxies {
	private String[] ip = {
			"51.15.2.43", //NL
			"194.28.50.32", //PL
			"112.201.91.20", // PH
			"185.93.3.123", // ES
			"185.82.212.95", // CZ
			"47.206.51.67", //US
			"154.119.50.246" //ZA
			};

	private int[] port = {
			3128,
			3128,
			8080,
			8080,
			8080,
			8080,
			53281

			};
	
	public Proxies() {
		
	}

	public String[] getIp() {
		return ip;
	}

	public int[] getPort() {
		return port;
	}
}

