package com.gns4.other;

import java.util.ArrayList;

public class RoutingTable {

  ArrayList<Route> routes;

  /**
   * @return The Route to the next hop or null if no matching route exists
   */
  public Route findNextHop(IPv4NetworkAddress dstIp) {
    Route resRoute = null;
    for (Route r : routes) {
      if (r.matches(dstIp)
          && (resRoute == null || r.getAddress().compareSubnet(resRoute.getAddress()) > 0)) {
        resRoute = r;
      }
    }
    return resRoute;
  }
}
