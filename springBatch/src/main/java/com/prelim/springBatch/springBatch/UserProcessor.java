package com.prelim.springBatch.springBatch;

import org.springframework.batch.item.ItemProcessor;


public class UserProcessor implements ItemProcessor<User, User> {


    @Override
    public User process(User user) throws Exception {
        return user;
    }
}
