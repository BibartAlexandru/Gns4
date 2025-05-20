package com.gns4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.gns4.networking_messages.ARPRequest;
import com.gns4.networking_messages.ARPResponse;
import com.gns4.networking_messages.DHCPAckgnowledge;
import com.gns4.networking_messages.DHCPDiscover;
import com.gns4.networking_messages.DHCPOffer;
import com.gns4.networking_messages.DHCPRequest;
import com.gns4.networking_messages.Frame;
import com.gns4.networking_messages.FrameHeader;
import com.gns4.networking_messages.FrameTrailer;
import com.gns4.networking_messages.ICMPMessageType;
import com.gns4.networking_messages.ICMPPacket;
import com.gns4.networking_messages.Packet;
import com.gns4.networking_messages.PacketHeader;
import com.gns4.networking_messages.decoders.ARPRequestDecoder;
import com.gns4.networking_messages.decoders.ARPResponseDecoder;
import com.gns4.networking_messages.decoders.DHCPAckgnowledgeDecoder;
import com.gns4.networking_messages.decoders.DHCPDiscoverDecoder;
import com.gns4.networking_messages.decoders.DHCPOfferDecoder;
import com.gns4.networking_messages.decoders.DHCPRequestDecoder;
import com.gns4.networking_messages.decoders.FrameDecoder;
import com.gns4.networking_messages.decoders.FrameHeaderDecoder;
import com.gns4.networking_messages.decoders.FrameTrailerDecoder;
import com.gns4.networking_messages.decoders.ICMPPacketDecoder;
import com.gns4.networking_messages.decoders.PacketHeaderDecoder;
import com.gns4.other.IPv4NetworkAddress;
import com.gns4.other.MAC;



public class Tests {
	
	@Test
	public void ARPReqEncTest() throws Exception {
		var a1 = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST);
		var enc = a1.encode();
		var dec = new ARPRequestDecoder();
		var a2 = dec.decode(enc);
		assert a1.equals(a2);
	}
	
	@Test
	public void ARPRespEncTest() throws Exception {
		var r1 = new ARPResponse(IPv4NetworkAddress.IP_BROADCAST, MAC.MAC_BROADCAST);
		var enc = r1.encode();
		var dec = new ARPResponseDecoder();
		var r2 = dec.decode(enc);
		assert r1.equals(r2);
	}
	
	@Test
	public void DHCPACKEncTest() throws Exception {
		var ack1 = new DHCPAckgnowledge(IPv4NetworkAddress.IP_BROADCAST);
		var enc = ack1.encode();
		var ackDec = new DHCPAckgnowledgeDecoder();
		var ack2 = ackDec.decode(enc);
		assert ack1.equals(ack2);
	}
	
	@Test
	public void DHCPDISCEncTest() throws Exception {
		var d1 = new DHCPDiscover();
		var enc = d1.encode();
		var ackDec = new DHCPDiscoverDecoder();
		var d2 = ackDec.decode(enc);
		assert d1.equals(d2);
	}
	
	@Test
	public void DHCPOFFEncTest() throws Exception {
		ArrayList<IPv4NetworkAddress> offers = new ArrayList<>();
		offers.add(IPv4NetworkAddress.ZERO);
		var o1 = new DHCPOffer(offers);
		var enc = o1.encode();
		var dec = new DHCPOfferDecoder();
		var o2 = dec.decode(enc);
		assert o1.equals(o2);
	}
	
	@Test
	public void DHCPREQEncTest() throws Exception {
		var req1 = new DHCPRequest(IPv4NetworkAddress.IP_BROADCAST);
		var enc = req1.encode();
		var dec = new DHCPRequestDecoder();
		var req2 = dec.decode(enc);
		assert req1.equals(req2);
	}
	
	@Test
	public void FrameHeaderEncTest() throws Exception {
		FrameHeader h = new FrameHeader(new MAC("12:12:12:12:12:12"), new MAC("0001.abde.02cf"), FrameHeader.TYPE_FIELD_IPv4);
		FrameHeaderDecoder decoder = new FrameHeaderDecoder();
		FrameHeader h2 = decoder.decode(h.encode());
		if(!h.equals(h2))
			throw new Exception("Decoder failed");
		System.out.println("All good");
	}
	
	@Test
	public void FrameTrailerEncTest() throws Exception {
		var tr = new ArrayList<Byte>();
		for(int i = 0 ; i < 4 ; i++)
			tr.add((byte)0);
		FrameTrailer f1 = new FrameTrailer(tr);
		var enc = f1.encode();
		var dec = new FrameTrailerDecoder();
		var f2 = dec.decode(enc);
		assert f1.equals(f2);
	}
	
	@Test
	public void ICMPPacketEncTest() throws Exception {
		var p1 = new ICMPPacket(ICMPMessageType.ECHO, "This is an example hehe");
		var dec = new ICMPPacketDecoder();
		var enc = p1.encode();
		var p2 = dec.decode(enc);
		assert p1.equals(p2);
	}
	
	@Test
	public void PacketHeaderEncTest() throws Exception {
		var ip1 = new IPv4NetworkAddress(new byte[] {0,0,0,0}, new byte[] {0,0,0,0});
		var ip2 = new IPv4NetworkAddress(new byte[] {0,0,0,0}, new byte[] {0,0,0,0});
		var p = new PacketHeader(ip1,ip2);
		var pDec = new PacketHeaderDecoder();
		var end = p.encode();
		var p2 = pDec.decode(p.encode());
		assert p.equals(p2);
	}
	
	@Test
	public void frameWithARPReqTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			var pl = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST);
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void frameWithARPRespTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			var pl = new ARPResponse(IPv4NetworkAddress.IP_BROADCAST, MAC.MAC_BROADCAST);
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithDHCPDiscTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new DHCPDiscover();
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithDHCPOffTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var offeredIps = new ArrayList<IPv4NetworkAddress>();
			offeredIps.add(IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new DHCPOffer(offeredIps);
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithDHCPReqTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new DHCPRequest(IPv4NetworkAddress.IP_BROADCAST);
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithDHCPAckTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new DHCPAckgnowledge(IPv4NetworkAddress.IP_BROADCAST);
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithICMPEchoTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new ICMPPacket(ICMPMessageType.ECHO, "dsadsaasddas");
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}
	
	@Test
	public void packetWithICMPReplyTest() throws Exception {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST, FrameHeader.TYPE_FIELD_IPv4);
			var tr = new ArrayList<Byte>();
			for(int i = 0 ; i < 4 ; i++)
				tr.add((byte)0);
			var ft = new FrameTrailer(tr);
			
			// packet stuff
			var pH = new PacketHeader(IPv4NetworkAddress.IP_BROADCAST, IPv4NetworkAddress.IP_BROADCAST);
			var l3Pl = new ICMPPacket(ICMPMessageType.REPLY, "dsadsasdasddasasdasdasdsad");
			var pl = new Packet(pH, l3Pl);
			
			var f = new Frame(fh, pl ,ft);
			
			var enc = f.encode();
			var fDec = new FrameDecoder();
			var decoded = fDec.decode(enc);
			
			assertEquals(true, f.equals(decoded));
	}

}
