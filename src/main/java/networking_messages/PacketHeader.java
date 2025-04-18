package networking.networking_messages;
import java.util.ArrayList;

import networking.networking_messages.decoders.PacketHeaderDecoder;
import networking.other.IPv4NetworkAddress;

/**
 * 9 bytes
 */
public class PacketHeader implements ByteSerializable<PacketHeader>{
	
	public static final int NR_BYTES = 2*IPv4NetworkAddress.NR_BYTES + 1;
	
	public PacketHeader(IPv4NetworkAddress sourceIp, IPv4NetworkAddress destIp, int ttl) throws Exception {
		super();
		if(ttl >= 256 || ttl < 0)
			throw new Exception("TTL must be between 0 and 255");
		this.sourceIp = sourceIp;
		this.destIp = destIp;
		this.ttl = ttl;
	}


	private IPv4NetworkAddress sourceIp;
	private IPv4NetworkAddress destIp;
	/**
	 * Between 0-255
	 */
	private int ttl;
	
	public IPv4NetworkAddress getDestIp() {
		return destIp;
	}
	public void setDestIp(IPv4NetworkAddress destIp) {
		this.destIp = destIp;
	}
	public IPv4NetworkAddress getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(IPv4NetworkAddress sourceIp) {
		this.sourceIp = sourceIp;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) throws Exception {
		if(ttl >= 256 || ttl < 0)
			throw new Exception("TTL must be between 0 and 255");
		this.ttl = ttl;
	}
	
	public boolean equals(PacketHeader other) {
		return sourceIp.equals(other.getSourceIp()) && destIp.equals(other.getDestIp()) && ttl == other.getTtl();
	}
	
	// 00000000 00000000 00000000 00000000
	@Override
	public ArrayList<Byte> encode() {
		var res = new ArrayList<Byte>();
		res.addAll(sourceIp.encode());
		res.addAll(destIp.encode());
		res.add((Byte) (byte) ttl);
		
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		var ip1 = new IPv4NetworkAddress(new byte[] {0,0,0,0}, new byte[] {0,0,0,0});
		var ip2 = new IPv4NetworkAddress(new byte[] {0,0,0,0}, new byte[] {0,0,0,0});
		var p = new PacketHeader(ip1,ip2,5);
		var pDec = new PacketHeaderDecoder();
		var end = p.encode();
		var p2 = pDec.decode(p.encode());
		if(!p.equals(p2))
			throw new Exception("FAILED");
		
	}
}
