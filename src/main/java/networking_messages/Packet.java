package main.java.networking_messages;

import java.util.ArrayList;
import java.util.List;

import jade.imtp.leap.JICP.JICPPacket;
import main.java.networking_messages.decoders.PacketDecoder;
import main.java.other.IPv4NetworkAddress;



public class Packet extends FramePayload {
	private PacketHeader header;
	private PacketPayload payload;
	public Packet(PacketHeader header, PacketPayload payload) {
		this.header = header;
		this.payload = payload;
	}
	public PacketHeader getHeader() {
		return header;
	}
	public void setHeader(PacketHeader header) {
		this.header = header;
	}
	public PacketPayload getPayload() {
		return payload;
	}
	public void setPayload(PacketPayload payload) {
		this.payload = payload;
	}
	
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.addAll(header.encode());
		res.addAll(payload.encode());
		return res;
	}
	
	public boolean equals(Packet other) {
		return header.equals(other.getHeader()) && payload.equals(other.getPayload());
	}
	
	public static void main(String[] args) throws Exception {
		var h = new PacketHeader(
				new IPv4NetworkAddress(new byte[] {1,2,3,4}, new byte[]{0,0,0,0}),
				new IPv4NetworkAddress(new byte[] {1,2,3,4}, new byte[]{0,0,0,0}),
				3);
		var pl = new DHCPDiscover();
		var p1 = new Packet(h,pl);
		var dec = new PacketDecoder();
		var p2 = dec.decode(p1.encode());
		if(!p1.equals(p2))
			throw new Exception("FAILED");
	}
	
}
