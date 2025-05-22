package com.gns4.networking_messages;

import java.util.ArrayList;
import com.gns4.helper.PrettyPrintable;
import com.gns4.networking_messages.decoders.PacketDecoder;
import com.gns4.other.IPv4NetworkAddress;

import de.vandermeer.asciitable.AsciiTable;



public class Packet extends Layer2Payload implements PrettyPrintable {
	public static final int MIN_BYTES = 1 + PacketHeader.NR_BYTES;
	private PacketHeader header;
	private Layer3Payload payload;
	
	public Packet(PacketHeader header, Layer3Payload payload) {
		this.header = header;
		this.payload = payload;
	}
	public PacketHeader getHeader() {
		return header;
	}
	public void setHeader(PacketHeader header) {
		this.header = header;
	}
	public Layer3Payload getPayload() {
		return payload;
	}
	public void setPayload(Layer3Payload payload) {
		this.payload = payload;
	}
	
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.add((byte)Layer2PayloadTypes.PACKET.ordinal());
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
				new IPv4NetworkAddress(new byte[] {1,2,3,4}, new byte[]{0,0,0,0})
				);
		var pl = new DHCPDiscover();
		var p1 = new Packet(h,pl);
		var dec = new PacketDecoder();
		var p2 = dec.decode(p1.encode());
		if(!p1.equals(p2))
			throw new Exception("FAILED");
	}
	@Override
	public String prettyPrint() {
    AsciiTable table = new AsciiTable() ;
    table.addRule();
    table.addRow("Version", "IHL",
      "Total length", "ID", "Flags", "Fragment Offset", "TTL", "Protocol", "Header Checksum", "Src IP", "Dst IP") ;
    table.addRule();
    table.addRow(header.getVersion(), header.getIdentification(), 
      header.getFlags(), header.getTotalLength(), header.getFragmentOffset(), 
      header.getTtl(), header.getProtocol(), header.getHeaderChechsum(), 
      header.getSourceIp(), header.getDestIp());
    return table.render() ;
	}
	
}
