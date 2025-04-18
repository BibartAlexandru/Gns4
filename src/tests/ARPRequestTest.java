package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import networking.networking_messages.ARPRequest;
import networking.networking_messages.decoders.ARPRequestDecoder;
import networking.other.IPv4NetworkAddress;

public class ARPRequestTest {
	@Test
	void decode() throws Exception {
		var a1 = new ARPRequest(IPv4NetworkAddress.IP_BROADCAST);
		var enc = a1.encode();
		var dec = new ARPRequestDecoder();
		var a2 = dec.decode(enc);
		assertEquals(true, a1.equals(a2));
	}

}
