package com.mehmetozanguven.springip2Location.controller;

import com.mehmetozanguven.springip2Location.service.MyIpLocation;
import com.mehmetozanguven.springip2Location.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IpLocationController {
    private static final Logger logger = LoggerFactory.getLogger(IpLocationController.class);
    private static final String IPV4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";


    private final MyIpLocation myIpLocation;

    public IpLocationController(@Autowired @Qualifier("ip2Location") MyIpLocation myIpLocation) {
        this.myIpLocation = myIpLocation;
    }

    @GetMapping("/myLocation")
    public String findClientLocation() {
        String clientIpAddress = RequestUtils.getClientIpAddress();
        logger.info("Client ip address: {}",clientIpAddress);
        return myIpLocation.findIpLocation(clientIpAddress);
    }

    @GetMapping("/findLocation/{ipAddress}")
    public String findIpLocation(@PathVariable  String ipAddress) {
        if (ipAddress.matches(IPV4_REGEX)) {
            return myIpLocation.findIpLocation(ipAddress);
        } else {
            return "undefined";
        }
    }
}
