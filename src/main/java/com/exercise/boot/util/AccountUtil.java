package com.exercise.boot.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountUtil {
    public long generateRandomAccountId(Random random) {
        return 100000000L + random.nextInt(900000000);
    }
}
