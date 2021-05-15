package com.mehmetozanguven.springip2Location.schedule;

import com.mehmetozanguven.springip2Location.handler.*;
import com.mehmetozanguven.springip2Location.properties.Ip2LocationProperties;
import com.mehmetozanguven.springip2Location.service.Ip2Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Ip2LocationSchedule {
    private static final Logger logger = LoggerFactory.getLogger(Ip2LocationSchedule.class);

    public static final String EACH_WEEK_ON_FRIDAY_AT_05_30 = "0 30 5 * * 5"; // 2021-04-09 05:30:00 (Friday)
    public static final String EVERY_TWO_MINS = "0 */1 * * * *";

    private final Ip2Location ip2Location;
    private final Ip2LocationProperties ip2LocationProperties;

    public Ip2LocationSchedule(@Autowired Ip2Location ip2Location,
                               @Autowired Ip2LocationProperties ip2LocationProperties) {
        this.ip2Location = ip2Location;
        this.ip2LocationProperties = ip2LocationProperties;
    }

    @Scheduled(cron = EVERY_TWO_MINS)
    public void downloadIp2LocationDatabase() {
        final String methodName = "downloadIp2LocationDatabase";
        logger.info("method: {}, scheduled job is running", methodName);
        logger.info("Trying to download ip2location lite db");
        Ip2LocationDownloadHandlerObject handlerObject = new Ip2LocationDownloadHandlerObject();

        Ip2LocationDownloadHandler downloadHandler = new DownloadIp2LocationBinFile(ip2LocationProperties);
        Ip2LocationDownloadHandler unzipIp2Location = new UnzipIp2Location(ip2LocationProperties);
        Ip2LocationDownloadHandler loadNewIp2Location = new LoadNewIp2LocationBinFile(ip2LocationProperties, ip2Location);

        downloadHandler.setNextHandler(unzipIp2Location);
        unzipIp2Location.setNextHandler(loadNewIp2Location);

        downloadHandler.handle(handlerObject);
    }

}
