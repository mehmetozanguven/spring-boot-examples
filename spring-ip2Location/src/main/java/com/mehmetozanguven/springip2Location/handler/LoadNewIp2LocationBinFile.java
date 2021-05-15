package com.mehmetozanguven.springip2Location.handler;

import com.mehmetozanguven.springip2Location.properties.Ip2LocationProperties;
import com.mehmetozanguven.springip2Location.service.Ip2Location;
import com.mehmetozanguven.springip2Location.service.Ip2LocationServiceImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LoadNewIp2LocationBinFile extends Ip2LocationDownloadHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoadNewIp2LocationBinFile.class);

    private final Ip2LocationProperties ip2LocationProperties;
    private final Ip2Location ip2LocationService;

    public LoadNewIp2LocationBinFile(Ip2LocationProperties ip2LocationProperties, Ip2Location ip2Location) {
        this.ip2LocationProperties = ip2LocationProperties;
        this.ip2LocationService = ip2Location;
    }

    @Override
    public void handle(Ip2LocationDownloadHandlerObject handlerObject) {
        if (handlerObject.isFileCanBeOpen()) {
            File newBinFile = new File(ip2LocationProperties.getFolder() + Ip2LocationServiceImpl.NEW_BIN_FILE);
            File currentBinFile = new File(ip2LocationProperties.getFolder() + Ip2LocationServiceImpl.BIN_FILE);
            try {
                FileUtils.copyFile(newBinFile ,currentBinFile);
                ip2LocationService.reloadIp2LocationBin();
            } catch (IOException e) {
                logger.error("Can not copy the new bin file to the current bin file.", e);
                throw new RuntimeException("Expected error for ip2location handler");
            }
        }
    }
}
