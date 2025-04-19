package com.gns4.networking_messages;

import java.util.ArrayList;
import java.util.List;

import com.gns4.helper.ByteSerializable;


/**
 * @encrypted 4 bytes
 */
public class FrameTrailer implements ByteSerializable<FrameTrailer>{
	
	public static final int NR_BYTES = 4; 
	// always 4 bytes
	private ArrayList<Byte> FCS;

	public ArrayList<Byte> getFCS()  {
		return FCS;
	}

	public void setFCS(ArrayList<Byte> fCS) throws Exception{
		if(fCS.size() != 4)
			throw new Exception("FCS " + FCS + " has an invalid length of" + FCS.size());
		FCS = fCS;
	}

	@Override
	public ArrayList<Byte> encode() {
		return FCS;
	}
	
	public boolean equals(FrameTrailer other) {
		return this.FCS.equals(other.getFCS());
	}

	public FrameTrailer(List<Byte> fCS) throws Exception {
		super();
		if(fCS.size() != NR_BYTES)
			throw new Exception("FCS " + fCS + " has an invalid length of" + fCS.size());
		FCS = new ArrayList<>(fCS);
	}

	
}
