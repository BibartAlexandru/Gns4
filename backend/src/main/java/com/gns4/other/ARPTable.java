package com.gns4.other;

import com.gns4.helper.Pair;
import java.util.HashMap;

public class ARPTable {
  private HashMap<IPv4NetworkAddress, MAC> table;

  public ARPTable(){
    this.table = new HashMap<>() ;
  }

  public boolean existsEntry(IPv4NetworkAddress address) {
    return table.containsKey(address);
  }

  public boolean existsEntry(MAC macAddress) {
    return table.containsValue(macAddress);
  }

  public Pair<IPv4NetworkAddress, MAC> getEntry(IPv4NetworkAddress address) {
    var p = new Pair<>(address, table.get(address));
    return p;
  }

  public Pair<IPv4NetworkAddress, MAC> getEntry(MAC macAddress) {
    IPv4NetworkAddress ip = null;
    for (var entry : table.entrySet()) if (entry.getValue().equals(macAddress)) ip = entry.getKey();
    var p = new Pair<>(ip, macAddress);
    return p;
  }

  public void add(IPv4NetworkAddress ip, MAC mac) {
    table.put(ip, mac);
  }

  public void remove(IPv4NetworkAddress ip) {
    table.remove(ip);
  }

  public void remove(MAC mac) {
    for (var entry : table.entrySet())
      if (entry.getValue().equals(mac)) {
        table.remove(entry.getKey());
        break;
      }
  }
}
