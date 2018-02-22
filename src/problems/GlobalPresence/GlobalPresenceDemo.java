package problems.GlobalPresence;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import utils.Common;

public class GlobalPresenceDemo {
	public static final String PROBLEM_URL 	= "a_global_presence/problem?access_token=942335cd269f9e0d";
	public static final String SOLUTION_URL = "a_global_presence/solve?access_token=942335cd269f9e0d";
	
	public static void sendRequests() throws Exception {
		String URL = "https://hackattic.com/_/presence/" + Common.httpGET(PROBLEM_URL);

		Proxies proxyList = new Proxies();
		URL server = new URL(URL);

		for (int i = 0; i < proxyList.getIp().length; i++) {

			Proxy proxy = new Proxy(Proxy.Type.HTTP,
					new InetSocketAddress(proxyList.getIp()[i], proxyList.getPort()[i]));

			HttpURLConnection connection = (HttpURLConnection) server.openConnection(proxy);
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			Common.showBytes(inputStream);
		}

	}

	public static void main(String[] args) throws Exception {
		sendRequests();
		
		String payload = "{}";
		Common.httpPOST(SOLUTION_URL, payload);
	}
}
