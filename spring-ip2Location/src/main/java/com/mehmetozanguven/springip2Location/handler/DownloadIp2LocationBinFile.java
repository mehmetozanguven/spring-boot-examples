package com.mehmetozanguven.springip2Location.handler;

import com.mehmetozanguven.springip2Location.properties.Ip2LocationProperties;
import com.mehmetozanguven.springip2Location.service.Ip2LocationServiceImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadIp2LocationBinFile extends Ip2LocationDownloadHandler {
    private static final String url = "https://www.ip2location.com/download/";
    private static final Logger logger = LoggerFactory.getLogger(DownloadIp2LocationBinFile.class);
    private final Ip2LocationProperties ip2LocationProperties;

    public DownloadIp2LocationBinFile(Ip2LocationProperties ip2LocationProperties) {
        this.ip2LocationProperties = ip2LocationProperties;
    }

    @Override
    public void handle(Ip2LocationDownloadHandlerObject handlerObject) {
        try {
            logger.info("Ip2 location download url: {}", generateDownloadUrl());
            URL downloadUrl = new URL(generateDownloadUrl());
            File destinationFile = new File(ip2LocationProperties.getFolder() + Ip2LocationServiceImpl.NEW_ZIP_FILE);
            FileUtils.copyURLToFile(downloadUrl, destinationFile);
            handlerObject.setFileDownloaded(true);
        } catch (IOException e) {
            logger.error("Could not download ip2Location bin file");
            handlerObject.setFileDownloaded(false);
        }
        getNextHandler().handle(handlerObject);
    }

    private String generateDownloadUrl() {
//        https://www.ip2location.com/download/?token={DOWNLOAD_TOKEN}&file={DATABASE_CODE}
        return url + "?token=" + ip2LocationProperties.getToken() + "&file=" + ip2LocationProperties.getDbName();
    }
}
