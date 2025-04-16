package networking.networking_messages;

import java.net.InetAddress;
import java.util.ArrayList;

public class DHCPOffer extends DHCPPacket{
	private ArrayList<InetAddress> offeredIps;

	public ArrayList<InetAddress> getOfferedIps() {
		return offeredIps;
	}

	public void setOfferedIps(ArrayList<InetAddress> offeredIps) {
		this.offeredIps = offeredIps;
	}
}
