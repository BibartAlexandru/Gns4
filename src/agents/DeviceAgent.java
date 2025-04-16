package agents;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import networking.networking_messages.ARPPacket;
import networking.networking_messages.DHCPPacket;
import networking.networking_messages.Frame;
import networking.networking_messages.ICMPPacket;
import networking.networking_messages.Packet;
import networking.other.Interface;
import networking.other.MAC;

public class DeviceAgent extends Agent {
	 private ArrayList<Interface> interfaces;
	 // destIp -> int name
	 private HashMap<InetAddress, String> routingTable;
	 private HashMap<InetAddress, MAC> arpTable;
	 private void sendFrame(Frame msg, MAC dstMac) {
		 
	 }
	 private void sendPacket(Packet msg, MAC dstMac, InetAddress dstIPv4) {
		 
	 }
	 private void sendARP(ARPPacket msg, MAC dstMac) {
		 
	 }
	 private void sendDHCP(DHCPPacket msg, MAC dstMac, InetAddress dstIpv4) {
		 
	 }
	 private void sendICMP(ICMPPacket msg, MAC dstMac, InetAddress dstIpv4) {
		 
	 }
	 
	 private void handleMessage(byte[] msg) {
		 
	 }
	 
	public ArrayList<Interface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(ArrayList<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	public HashMap<InetAddress, String> getRoutingTable() {
		return routingTable;
	}
	public void setRoutingTable(HashMap<InetAddress, String> routingTable) {
		this.routingTable = routingTable;
	}
	public HashMap<InetAddress, MAC> getArpTable() {
		return arpTable;
	}
	public void setArpTable(HashMap<InetAddress, MAC> arpTable) {
		this.arpTable = arpTable;
	}
}
