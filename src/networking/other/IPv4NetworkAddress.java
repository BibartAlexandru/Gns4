package networking.other;

import java.util.ArrayList;

import networking.networking_messages.ByteSerializable;
import networking.networking_messages.decoders.IPv4NetworkAddressDecoder;

public class IPv4NetworkAddress implements ByteSerializable<IPv4NetworkAddress>{
	public static final IPv4NetworkAddress ZERO = new IPv4NetworkAddress(
		new byte[] {0,0,0,0},	
		new byte[] {0,0,0,0}
		);
	public static final IPv4NetworkAddress IP_BROADCAST = new IPv4NetworkAddress(
			new byte[] {(byte)255,(byte)255,(byte)255, (byte)255},	
			new byte[] {(byte)255,(byte)255,(byte)255, (byte)255}
			);	
	
	public static final int NR_BYTES = 8;
	
	// byte is signed =(
	private byte[] ip;
	private byte[] subnetMask;
	public IPv4NetworkAddress(byte[] ip, byte[] subnetMask) {
		super();
		this.setIp(ip);
		this.setSubnetMask(subnetMask);
	}
	public byte[] getIp() {
		return ip;
	}
	public void setIp(byte[] ip) {
		this.ip = ip;
	}
	public byte[] getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(byte[] subnetMask) {
		this.subnetMask = subnetMask;
	}
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		for(byte b : ip)
			res.add(b);
		for(byte b: subnetMask)
			res.add(b);
		return res;
	}
	
	public boolean equals(IPv4NetworkAddress other) {
		for(int i = 0 ; i < 4 ; i++)
			if(ip[i] != other.getIp()[i] || subnetMask[i] != other.getSubnetMask()[i])
				return false;
		return true ;
	}
	
	public static void main(String[] args) throws Exception {
		IPv4NetworkAddress i1 = new IPv4NetworkAddress(new byte[] {0,(byte) 255,0,0}, new byte[] {0,(byte) 255,0,0});
		IPv4NetworkAddressDecoder dec = new IPv4NetworkAddressDecoder();
		IPv4NetworkAddress i2 = dec.decode(i1.encode());
		if(!i1.equals(i2))
			throw new Exception("FAILED");
//		System.out.println(Byte.toUnsignedInt(i1.ip[1]));
	}
	
}
