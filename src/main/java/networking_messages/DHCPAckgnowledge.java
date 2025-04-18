package main.java.networking_messages;

import java.net.InetAddress;
import java.util.ArrayList;

import main.java.networking_messages.decoders.DHCPAckgnowledgeDecoder;
import main.java.other.IPv4NetworkAddress;


public class DHCPAckgnowledge extends DHCPPacket{
	public static final byte DHCPACK_CODE = 3;
	public static final int NR_BYTES = 9;
	private IPv4NetworkAddress ackgnowledgedIp;

	public DHCPAckgnowledge(IPv4NetworkAddress ackgnowlegedIp) {
		this.ackgnowledgedIp = ackgnowlegedIp;
	}
	
	public IPv4NetworkAddress getAckgnowledgedIp() {
		return ackgnowledgedIp;
	}

	public void setAckgnowledgedIp(IPv4NetworkAddress ackgnowledgedIp) {
		this.ackgnowledgedIp = ackgnowledgedIp;
	}
	
	public boolean equals(DHCPAckgnowledge other) {
		return ackgnowledgedIp.equals(other);
	}
	
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add(DHCPACK_CODE);
		res.addAll(ackgnowledgedIp.encode());
		return res;
	}
	
	public static void main(String[] args) {
		var ack1 = new DHCPAckgnowledge(IPv4NetworkAddress.IP_BROADCAST);
		var enc = ack1.encode();
		var ackDec = new DHCPAckgnowledgeDecoder();
		var ack2 = ackDec.decode(enc);
		assert ack1.equals(ack2);
	}
}
