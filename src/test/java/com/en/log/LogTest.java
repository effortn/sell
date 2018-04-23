package com.en.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by En on 2018/4/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogTest {

    @Test
    public void testLog1() {
        log.debug("debug.....");
        log.info("info.....");
        log.warn("warn.....");
        log.error("error.....");
    }

}
