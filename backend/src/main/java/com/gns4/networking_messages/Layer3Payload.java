package com.gns4.networking_messages;

import java.util.ArrayList;

import com.gns4.helper.ByteSerializable;


public abstract class Layer3Payload implements ByteSerializable<Layer3Payload> {

	@Override
	public ArrayList<Byte> encode() {
		return new ArrayList<>();
	}
	
	public boolean equals(Layer3Payload other) {
		return true;
	}

}
