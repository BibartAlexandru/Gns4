package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ARPResponse;
import networking.networking_messages.ByteArrayDecoder;
import networking.other.IPv4NetworkAddress;
import networking.other.MACDecoder;

public class ARPResponseDecoder extends ARPPacketDecoder{

	@Override
	public ARPResponse decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != ARPResponse.NR_BYTES)
			throw new Exception("ARPResponse: " + bytes.size() + " has invalid length: " + bytes.size());
		byte code = bytes.get(0);
		if(code != ARPResponse.ARPRESPONSE_CODE)
			throw new Exception("ARPResponse: " + bytes + " has invalid code: " + code);
		var ipDec = new IPv4NetworkAddressDecoder();
		var macDec = new MACDecoder();
		var reqIp = ipDec.decode(bytes.subList(1, 1 + IPv4NetworkAddress.NR_BYTES));
		var respMAC = macDec.decode(bytes.subList(1 + IPv4NetworkAddress.NR_BYTES, bytes.size()));
		return new ARPResponse(reqIp, respMAC);
	}

}
