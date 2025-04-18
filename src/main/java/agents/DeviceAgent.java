package main.java.agents;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import main.java.helper.ByteSerializable;
import main.java.helper.Helper;
import main.java.networking_messages.ARPPacket;
import main.java.networking_messages.DHCPPacket;
import main.java.networking_messages.Frame;
import main.java.networking_messages.ICMPPacket;
import main.java.networking_messages.Packet;
import main.java.networking_messages.decoders.FrameDecoder;
import main.java.other.Interface;
import main.java.other.MAC;


public class DeviceAgent extends Agent {
	 private ArrayList<Interface> interfaces;
	 // destIp -> int name
	 private HashMap<InetAddress, String> routingTable;
	 private HashMap<InetAddress, MAC> arpTable;
	 
	 /**
	  * 
	  * @param f
	  * @param dstMac
	  * @param to
	  * 
	  * Sends encoded as byte[]
	  */
	 private<T> void sendByteSerializable(ByteSerializable<T> f, Interface to) {
		 ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		 byte[] msgContent = Helper.toByteArr(f.encode());
		 msg.setByteSequenceContent(msgContent);
		 msg.addReceiver(to.getAgent().getAID());
		 send(msg);
	 }
	 
	 private void handleMessage(byte[] msg) {
		 var frameDec = new FrameDecoder();
		 try {
			 var frame = frameDec.decode(Helper.toByteArrList(msg));
			 
		 }
		 catch(Exception e) {
			 System.out.println("Agent: " + getLocalName() + " failed to parse frame: " + msg);
		 }
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
	
	public DeviceAgent() {
		super();
	}
}
