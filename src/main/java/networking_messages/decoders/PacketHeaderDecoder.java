package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.PacketHeader;

public class PacketHeaderDecoder extends ByteArrayDecoder<PacketHeader>{

	@Override
	public PacketHeader decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != PacketHeader.NR_BYTES)
			throw new Exception("Size of packet header should be 17 for header " + bytes);
		var ipDec = new IPv4NetworkAddressDecoder();
		var srcIp = ipDec.decode(bytes.subList(0, 8));
		var dstIp = ipDec.decode(bytes.subList(8, 16));
		int ttl = bytes.get(16);
		return new PacketHeader(srcIp, dstIp, ttl);
	}

}
