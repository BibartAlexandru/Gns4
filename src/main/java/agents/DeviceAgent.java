package main.java.agents;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import main.java.networking_messages.ARPPacket;
import main.java.networking_messages.DHCPPacket;
import main.java.networking_messages.Frame;
import main.java.networking_messages.ICMPPacket;
import main.java.networking_messages.Packet;
import main.java.other.Interface;
import main.java.other.MAC;


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
