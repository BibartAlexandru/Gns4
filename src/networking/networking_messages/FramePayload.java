package networking.networking_messages;

import java.util.ArrayList;
import java.util.List;

public class FramePayload implements ByteSerializable<FramePayload>{

	public FramePayload() {
		
	}
	
	@Override
	public ArrayList<Byte> encode() {
		ArrayList<Byte> res = new ArrayList<Byte>();
		return res;
	}
	
	/**
	 *
	 * MUST use to pad the encoded frame to MIN_SIZE of 64 bytes
	 */
	 
	public void padPayload(ArrayList<Byte> payload, int nrBytes) {
		for(int i = 0 ; i < nrBytes; i++)
			payload.add((byte)0);
	}
	
	public boolean equals(FramePayload other) {
		return true;
	}

}
