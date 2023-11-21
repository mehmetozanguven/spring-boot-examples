package com.mehmetozanguven.capturelongrunningmethod.controller;

import com.mehmetozanguven.capturelongrunningmethod.capture.LongRunningExecution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/every-request")
    @LongRunningExecution(request = true)
    public String logEveryRequest(@RequestParam(name = "dummy") String test) {
        // LongRunningExecution will log the every request for the method call
        return "log-every-request";
    }

    @GetMapping("/every-request-response")
    @LongRunningExecution(request = true, response = true)
    public String logEveryRequestAndResponse(@RequestParam(name = "dummy") String test) {
        // LongRunningExecution will log the every request & response for the method call
        return "log-every-request-response";
    }

    @GetMapping("/every-request-response-execution-time")
    @LongRunningExecution(request = true, response = true, maxExecutionTime = 5000)
    public String logEveryRequestAndResponseForMethodExecution() throws InterruptedException {
        // LongRunningExecution will log the every request & response if the method execution time is longer than or equal 5 seconds
        Thread.sleep(5001);
        return "log-every-request-response-execution-time";
    }
}
