package networking.networking_messages;

import java.net.InetAddress;

public class PacketHeader {
	private InetAddress sourceIp;
	private InetAddress destIp;
	private int ttl;
	public InetAddress getDestIp() {
		return destIp;
	}
	public void setDestIp(InetAddress destIp) {
		this.destIp = destIp;
	}
	public InetAddress getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(InetAddress sourceIp) {
		this.sourceIp = sourceIp;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
}
