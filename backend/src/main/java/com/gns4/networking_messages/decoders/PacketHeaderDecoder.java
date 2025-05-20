package com.gns4.networking_messages.decoders;

import java.util.List;

import com.gns4.helper.Helper;
import com.gns4.networking_messages.PacketHeader;


public class PacketHeaderDecoder extends ByteArrayDecoder<PacketHeader>{
	@Override
	public PacketHeader decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != PacketHeader.NR_BYTES)
			throw new Exception("Size of packet header should be " + PacketHeader.NR_BYTES+  "for header " + bytes);
		var ipDec = new IPv4NetworkAddressDecoder();
    byte version = bytes.get(0) ;
    int ihl = Helper.listToInt(bytes.subList(1, 5)) ;
    int totalLength = Helper.listToInt(bytes.subList(5, 9)) ;
    int identification = Helper.listToInt(bytes.subList(9, 13)) ;
    byte flags = bytes.get(13);
    int fragmentOffset = Helper.listToInt(bytes.subList(14, 18)) ;
    int ttl = Helper.listToInt(bytes.subList(18, 22)) ;
    byte protocol = bytes.get(22) ;
    byte[] headerChecksum = Helper.toByteArr(bytes.subList(23, 25));
		var srcIp = ipDec.decode(bytes.subList(25, 33));
		var dstIp = ipDec.decode(bytes.subList(33, 41));
		return new PacketHeader(version, ihl, totalLength, identification,
      flags, fragmentOffset, ttl, protocol, headerChecksum, srcIp, dstIp);
	}

}
