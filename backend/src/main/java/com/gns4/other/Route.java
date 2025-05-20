package com.gns4.other;

import com.gns4.helper.Helper;

public class Route {
  IPv4NetworkAddress address;

  public IPv4NetworkAddress getAddress() {
    return address;
  }

  public void setAddress(IPv4NetworkAddress address) {
    this.address = address;
  }

  RouteType type;

  public RouteType getType() {
    return type;
  }

  public void setType(RouteType type) {
    this.type = type;
  }

  public boolean matches(IPv4NetworkAddress address) {
    try {
      int thisAddr = Helper.toInt(this.address.getIp());
      int otherAddr = Helper.toInt(address.getIp());
      int thisSubMask = Helper.toInt(this.address.getSubnetMask());
      if ((thisAddr & thisSubMask) == (otherAddr & thisSubMask)) return true;
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
