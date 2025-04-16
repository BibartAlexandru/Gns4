package networking.networking_messages;

import java.net.InetAddress;

public class DHCPAckgnowledge {
	private InetAddress ackgnowledgedIp;

	public InetAddress getAckgnowledgedIp() {
		return ackgnowledgedIp;
	}

	public void setAckgnowledgedIp(InetAddress ackgnowledgedIp) {
		this.ackgnowledgedIp = ackgnowledgedIp;
	}
}
