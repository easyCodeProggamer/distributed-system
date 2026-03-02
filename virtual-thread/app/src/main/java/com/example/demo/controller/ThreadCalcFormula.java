package com.example.demo.controller;

import com.example.demo.service.BlockingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@RestController
public class ThreadCalcFormula {

    private static final Logger LOGGER = Logger.getLogger(ThreadCalcFormula.class.getName());

    @Autowired
    @Qualifier("osPlatformThreadExecutor")
    private ExecutorService executorService;


    @Autowired
    @Qualifier("customOsPlatformThreadExecutorWithThreadFactory")
    private ExecutorService executorServiceWithCustomFactory;


    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ExecutorService virtualThreadExecutor;

    @Autowired
    private BlockingService blockingService;



    @GetMapping("/platform/completableFuture/threadFactory/jdk8")
    public String platformCompletableFutureThreadFactoryExample(
            @RequestParam Integer counter) {

        List<CompletableFuture<String>> futures =
                IntStream.range(0, counter)
                        .mapToObj(i ->
                                CompletableFuture.supplyAsync(
                                        blockingService::process,
                                        executorServiceWithCustomFactory))
                        .toList();

        List<String> results =
                futures.stream()
                        .map(CompletableFuture::join)
                        .toList();


        LOGGER.info("Executor Service Results "+ results);

        return "success";
    }



    @GetMapping("/virtual/thread/jdk21")
    public String virtualThreadExample(@RequestParam Integer counter) throws InterruptedException {

        List<Callable<String>> tasks =
                IntStream.range(0, counter)
                        .mapToObj(i -> (Callable<String>) blockingService::process)
                        .toList();

        List<Future<String>> futures =
                virtualThreadExecutor.invokeAll(tasks);

        List<String> results =
                futures.stream()
                        .map(f -> {
                            try {
                                return f.get();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();

        LOGGER.info("Virtual Threads Results "+ results);

        return "success";
    }
}