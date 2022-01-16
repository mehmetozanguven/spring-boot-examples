package com.mehmetozanguven.springcachewithoutannotation.configuration;

import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class HazelcastCacheConfiguration {
    private static final String HAZELCAST_INSTANCE_NAME = "my-hazelcast-instance";


    @Bean
    public Config hazelCastConfig(){
        EvictionConfig evictionConfig = new EvictionConfig();
        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.USED_HEAP_SIZE);

        MapConfig cacheConfig = new MapConfig();
        cacheConfig.setName("my-cache-name");
        cacheConfig.setEvictionConfig(evictionConfig);
        cacheConfig.setTimeToLiveSeconds(3000);

        Config config = new Config();
        config.setInstanceName(HAZELCAST_INSTANCE_NAME)
                .addMapConfig(cacheConfig);
        return config;
    }
}
