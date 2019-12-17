package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class IpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("192.168.1.68");
    assertEquals(ipLocation, "<GeoIP><Country>US</Country><State>CA</State></GeoIP>");
  }

}
