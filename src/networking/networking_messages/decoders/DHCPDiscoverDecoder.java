package networking.networking_messages.decoders;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;
import networking.networking_messages.DHCPDiscover;

public class DHCPDiscoverDecoder extends ByteArrayDecoder<DHCPDiscover>{

	@Override
	public DHCPDiscover decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != 1 && bytes.get(0) != (byte)0)
			throw new Exception("Failed to parse DHCPDiscover " + bytes);
		return new DHCPDiscover();
	}

}
