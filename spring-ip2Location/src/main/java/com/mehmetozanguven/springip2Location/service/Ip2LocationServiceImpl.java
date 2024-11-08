package com.mehmetozanguven.springip2Location.service;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.mehmetozanguven.springip2Location.properties.Ip2LocationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("ip2Location")
public class Ip2LocationServiceImpl implements MyIpLocation, Ip2Location {
    private static final Logger logger = LoggerFactory.getLogger(Ip2LocationServiceImpl.class);

    public static final String IP2_LOCATION_BIN_FILE_NAME = "IP2LOCATION-LITE-DB1.BIN";

    public static final String NEW_ZIP_FILE =  "/new_IP2LOCATION-LITE-DB1.ZIP";
    public static final String NEW_BIN_FILE = "/new_IP2LOCATION-LITE-DB1.BIN";

    public static final String BIN_FILE = "/IP2LOCATION-LITE-DB1.BIN";

    private IP2Location ip2Location;

    private final Ip2LocationProperties ip2LocationProperties;

    public Ip2LocationServiceImpl(@Autowired Ip2LocationProperties ip2LocationProperties) {
        this.ip2LocationProperties = ip2LocationProperties;
        openIp2Location();
    }

    private void openIp2Location() {
        String filePath = ip2LocationProperties.getFolder() + BIN_FILE;
        this.ip2Location = new IP2Location();
        try {
            this.ip2Location.Open(filePath);
            logger.info("Ip2Location loaded");
        } catch (IOException e) {
            logger.error("Can not open the ip2Location", e);
            // You may throw exception or something else, when the path not exists
//            throw new RuntimeException("Can not open the ip2Location");
        }
    }

    @Override
    public void reloadIp2LocationBin() {
        openIp2Location();
    }

    @Override
    public String findIpLocation(String ipAddress) {
        try {
            IPResult ipResult = ip2Location.IPQuery(ipAddress);
            return ipResult.getCountryLong();
        } catch (IOException e) {
            logger.error("Error while ip lookup", e);
            return "undefined";
        }
    }
}
