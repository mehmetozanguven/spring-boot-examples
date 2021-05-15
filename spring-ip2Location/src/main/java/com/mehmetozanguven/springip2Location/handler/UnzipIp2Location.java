package com.mehmetozanguven.springip2Location.handler;

import com.mehmetozanguven.springip2Location.properties.Ip2LocationProperties;
import com.mehmetozanguven.springip2Location.service.Ip2LocationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipIp2Location extends Ip2LocationDownloadHandler {
    private static final Logger logger = LoggerFactory.getLogger(UnzipIp2Location.class);
    private final Ip2LocationProperties ip2LocationProperties;
    public UnzipIp2Location(Ip2LocationProperties ip2LocationProperties) {
        this.ip2LocationProperties = ip2LocationProperties;
    }


    @Override
    public void handle(Ip2LocationDownloadHandlerObject handlerObject) {
        Path zipFile = Path.of(ip2LocationProperties.getFolder() + Ip2LocationServiceImpl.NEW_ZIP_FILE);
        Path newIp2Location = Path.of(ip2LocationProperties.getFolder() + Ip2LocationServiceImpl.NEW_BIN_FILE);
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toFile()));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.getName().contains(Ip2LocationServiceImpl.IP2_LOCATION_BIN_FILE_NAME)) {
                    Files.copy(zis, newIp2Location, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Correctly load new ip2location file");
                }
                zipEntry = zis.getNextEntry();
            }
        } catch (Exception ex) {
            logger.error("Exception while unzipping ip2Location.zip", ex);
        }
        getNextHandler().handle(handlerObject);
    }
}
