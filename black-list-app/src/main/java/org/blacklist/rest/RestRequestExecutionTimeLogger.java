package org.blacklist.rest;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestRequestExecutionTimeLogger {

    public void logExecutionTime(Stopwatch stopwatch) {
        stopwatch.stop();
        long elapsedMillis = stopwatch.elapsed().toMillis();
        log.info("Request processing time (ms): {}", elapsedMillis);
    }
}
