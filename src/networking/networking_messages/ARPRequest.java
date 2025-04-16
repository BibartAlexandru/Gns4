package networking.networking_messages;

import java.net.InetAddress;

public class ARPRequest extends ARPPacket {
	private InetAddress requestedIp;

	public InetAddress getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(InetAddress requestedIp) {
		this.requestedIp = requestedIp;
	}
}
