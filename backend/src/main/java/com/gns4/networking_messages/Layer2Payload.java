package com.gns4.networking_messages;

import java.util.ArrayList;
import com.gns4.helper.ByteSerializable;
import com.gns4.helper.PrettyPrintable;

public abstract class Layer2Payload implements ByteSerializable<Layer2Payload>, PrettyPrintable{

	public Layer2Payload() {
		
	}
	
	@Override
	public ArrayList<Byte> encode() {
		return null;
	}
	
	/**
	 *
	 * MUST use to pad the encoded frame to MIN_SIZE of 64 bytes
	 */
	 
	public void padPayload(ArrayList<Byte> payload, int nrBytes) {
		for(int i = 0 ; i < nrBytes; i++)
			payload.add((byte)0);
	}
	
	public boolean equals(Layer2Payload other) {
		return true;
	}

}
