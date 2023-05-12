package com.mehmetozanguven.springcachewithoutannotation.service;

import com.mehmetozanguven.springcachewithoutannotation.model.User;
import com.mehmetozanguven.springcachewithoutannotation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserHazelcastServiceImpl userHazelcastService;

    public UserServiceImpl(UserRepository userRepository,
                           UserHazelcastServiceImpl userHazelcastService) {
        this.userRepository = userRepository;
        this.userHazelcastService = userHazelcastService;
    }


    @Override
    public User findById(long id) {
        User user = userHazelcastService.findByIdInCache(id);
        if (user != null) {
            logger.info("Found from the cache. User: {}", user);
            return user;
        }
        logger.info("User did not found in the cache. DB lookup...");
        User dbInUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid request"));
        userHazelcastService.putUserInTheCache(dbInUser);
        return dbInUser;
    }

    @Override
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).orElseThrow(() -> new RuntimeException("Invalid request"));
    }

    @Override
    public void changeFirstName(long id, String firstName) {
        User userInDb = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid request"));
        userInDb.setFirstName(firstName);
        userRepository.save(userInDb);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
