package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.networking_messages.ARPPacket;
import main.java.networking_messages.ARPRequest;
import main.java.networking_messages.ARPResponse;
import main.java.networking_messages.DHCPAckgnowledge;
import main.java.networking_messages.DHCPDiscover;
import main.java.networking_messages.DHCPOffer;
import main.java.networking_messages.DHCPRequest;
import main.java.networking_messages.Frame;
import main.java.networking_messages.FrameHeader;
import main.java.networking_messages.Layer2Payload;
import main.java.networking_messages.FrameTrailer;
import main.java.networking_messages.ICMPMessageType;
import main.java.networking_messages.ICMPPacket;
import main.java.networking_messages.Packet;
import main.java.networking_messages.PacketHeader;
import main.java.networking_messages.decoders.ARPRequestDecoder;
import main.java.networking_messages.decoders.ARPResponseDecoder;
import main.java.networking_messages.decoders.DHCPAckgnowledgeDecoder;
import main.java.networking_messages.decoders.DHCPDiscoverDecoder;
import main.java.networking_messages.decoders.DHCPOfferDecoder;
import main.java.networking_messages.decoders.DHCPRequestDecoder;
import main.java.networking_messages.decoders.FrameDecoder;
import main.java.networking_messages.decoders.FrameHeaderDecoder;
import main.java.networking_messages.decoders.FrameTrailerDecoder;
import main.java.networking_messages.decoders.ICMPPacketDecoder;
import main.java.networking_messages.decoders.PacketHeaderDecoder;
import main.java.other.IPv4NetworkAddress;
import main.java.other.MAC;

public class Tests {
	
	@Before
	public void setUp() {
	}
	
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
		FrameHeader h = new FrameHeader(new MAC("12:12:12:12:12:12"), new MAC("0001.abde.02cf"));
		FrameHeaderDecoder decoder = new FrameHeaderDecoder();
		FrameHeader h2 = decoder.decode(h.encode());
		if(h.equals(h2))
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
		var p = new PacketHeader(ip1,ip2,5);
		var pDec = new PacketHeaderDecoder();
		var end = p.encode();
		var p2 = pDec.decode(p.encode());
		assert p.equals(p2);
	}
	
	@Test
	public void frameDecTest() {
		try {
			var fh = new FrameHeader(MAC.MAC_BROADCAST, MAC.MAC_BROADCAST);
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
		catch(Exception e) {
			fail(e.toString());
		}
	}

}
