package ru.stqa.pft;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String geoIp = new GeoIPService().getGeoIPServiceSoap().getIpLocation("91.232.196.17");
        System.out.println(geoIp);
    }
}
