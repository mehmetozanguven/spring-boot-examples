package com.mehmetozanguven.springcachewithoutannotation.service;

import com.hazelcast.core.HazelcastInstance;
import com.mehmetozanguven.springcachewithoutannotation.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserHazelcastServiceImpl {
    private final HazelcastInstance hazelcastInstance;

    public UserHazelcastServiceImpl(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public User findByIdInCache(long userId) {
        Map<Long, User> userCache = hazelcastInstance.getMap("my-cache-name");
        return userCache.get(userId);
    }

    public void putUserInTheCache(User user) {
        Map<Long, User> userCache = hazelcastInstance.getMap("my-cache-name");
        userCache.put(user.getId(), user);
    }
}
