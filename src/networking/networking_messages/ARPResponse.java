package networking.networking_messages;

import java.net.InetAddress;

public class ARPResponse extends ARPPacket{
	private InetAddress requestedIp;
	private String responseMAC;
}
