package main.java.networking_messages.decoders;

import java.util.List;

import main.java.other.IPv4NetworkAddress;


public class IPv4NetworkAddressDecoder extends ByteArrayDecoder<IPv4NetworkAddress> {

	@Override
	public IPv4NetworkAddress decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != IPv4NetworkAddress.NR_BYTES)
			throw new Exception("Ip network address " +  bytes + " has an invalid size of " + bytes.size());
		byte[] ip = new byte[] {bytes.get(0), bytes.get(1), bytes.get(2), bytes.get(3)};
		byte[] subnetMask = new byte[] {bytes.get(4), bytes.get(5), bytes.get(6), bytes.get(7)};
		return new IPv4NetworkAddress(ip, subnetMask);
	}

}
