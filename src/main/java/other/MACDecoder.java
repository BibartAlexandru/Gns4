package networking.other;

import java.util.List;

import networking.networking_messages.ByteArrayDecoder;

public class MACDecoder extends ByteArrayDecoder<MAC>{

	@Override
	public MAC decode(List<Byte> bytes) throws Exception {
		if(bytes.size() != MAC.NR_BYTES)
			throw new Exception("MAC: " + bytes + " has an invalid size of " + bytes.size());
		return new MAC(new byte[] { 
				bytes.get(0),
				bytes.get(1),
				bytes.get(2),
				bytes.get(3),
				bytes.get(4),
				bytes.get(5),
				});
	}

}
