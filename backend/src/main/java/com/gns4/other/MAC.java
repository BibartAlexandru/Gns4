package com.gns4.other;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

import com.gns4.helper.ByteSerializable;



public class MAC implements ByteSerializable<MAC> {
	
	public static final int NR_BYTES = 6; 
	public static final MAC MAC_BROADCAST = new MAC(
			new byte[] {
					(byte) 255,
					(byte) 255,
					(byte) 255,
					(byte) 255,
					(byte) 255,
					(byte) 255,
			}
			);
	
	private byte[] address;

	public byte[] getAddress() {
		return address;
	}
	
	public String toString() {
		return "";
	}
	
	public MAC(byte[] address) {
		this.address = address;
	}
	
	private byte[] fromStringFormat(String address) {
		if(address.contains("."))
			address = address.replace(".", "");
		if(address.contains(":"))
			address = address.replace(":", "");
		address = address.toUpperCase();
		byte[] addr = new byte[NR_BYTES] ;
		for(int i = 0 ; i < NR_BYTES ; i+=1)
			addr[i] = (byte)Integer.parseInt(address.substring(2*i,2*i+2), 16);
		return addr;
	}
	
	/**
  	   Address can be of the format 0000.0000.0000 or 00:00:00:00:00:00 (case insensitive)
	 * Throws exception if format is not correct
	 * 
	 * @throws Exception 
	 */
	public void setAddress(String address) throws Exception {
		Pattern pattern = Pattern.compile("(^[a-f0-9]{4}\\.[a-f0-9]{4}\\.[a-f0-9]{4}$)|(^[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2})$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(address);
		
		if(!matcher.matches())
			throw new Exception("The string " + address + " is not a valid MAC address.");
		String format1 = matcher.group(1);
		String format2 = matcher.group(2);
		this.address = fromStringFormat(address.toLowerCase());
	}
	
	/**
	 * Transforms the address to 0000.0000.0000 format before returning the bytes of 
	 * the ASCII encoded MAC address string.
	 */
	public ArrayList<Byte> encode(){
		var res = new ArrayList<Byte>();
		for(byte b : address)
			res.add(b);
		return res;
	}
	
	public MAC(String address) throws Exception {
		setAddress(address);
	}
	
	public boolean equals(MAC other) {
		for(int i = 0 ; i < 6 ; i++)
			if(address[i] != other.getAddress()[i])
				return false;
		return true;
	}

  public static MAC getRandom(){
    byte[] address = new byte[]{
      (byte)ThreadLocalRandom.current().nextInt(0, 256),
      (byte)ThreadLocalRandom.current().nextInt(0, 256),
      (byte)ThreadLocalRandom.current().nextInt(0, 256),
      (byte)ThreadLocalRandom.current().nextInt(0, 256),
      (byte)ThreadLocalRandom.current().nextInt(0, 256),
      (byte)ThreadLocalRandom.current().nextInt(0, 256)
    };
    return new MAC(address);
  }  

	public static void main(String[] args) throws Exception {
		MAC m1 = new MAC("0412.0Ab0.0d30");
		MAC m3 = new MAC("12:a3:63:ab:82:aa");
		MAC m4 = new MAC("12a3.63Ab.82aa");
		var enc2 = m3.encode();
		var enc = m1.encode();
		var dec = new MACDecoder();
		var m2 = dec.decode(enc);
		assert m1.equals(m2);
		assert m3.equals(m4);
	}
}
