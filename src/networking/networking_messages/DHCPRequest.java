package networking.networking_messages;

import java.net.InetAddress;

public class DHCPRequest {
	private InetAddress requestedIp;

	public InetAddress getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(InetAddress requestedIp) {
		this.requestedIp = requestedIp;
	}
}
