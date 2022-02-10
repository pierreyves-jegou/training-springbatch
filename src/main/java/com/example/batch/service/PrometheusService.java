package com.example.batch.service;

import com.example.batch.config.PrometheusConfiguration;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrometheusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrometheusService.class);

    @Value("${prometheus.job.name:myJobName}")
    private String prometheusJobName;

    @Value("${prometheus.grouping.key:prometheusBatchJobName}")
    private String prometheusGroupingKey;

    @Value("${prometheus.pushgateway.url:localhost:9091}")
    private String prometheusPushGatewayUrl;

    private Map<String, String> groupingKey = new HashMap<>();
    private PushGateway pushGateway;
    private CollectorRegistry collectorRegistry;

    @PostConstruct
    public void init() {
        pushGateway = new PushGateway(prometheusPushGatewayUrl);
        groupingKey.put(prometheusGroupingKey, prometheusJobName);
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        collectorRegistry = prometheusMeterRegistry.getPrometheusRegistry();
        Metrics.globalRegistry.add(prometheusMeterRegistry);
    }

    public void addMetrics(String key){
        Metrics.counter(key).increment();
    }

    @Scheduled(fixedRateString = "${prometheus.push.rate:3}")
    public void pushMetrics() {
        try {
            pushGateway.pushAdd(collectorRegistry, prometheusJobName, groupingKey);
        } catch (Throwable ex) {
            LOGGER.error("Unable to push metrics to Prometheus Push Gateway", ex);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            pushGateway.pushAdd(collectorRegistry, prometheusJobName, groupingKey);
        } catch (Throwable ex) {
            LOGGER.error("Unable to push metrics to Prometheus Push Gateway", ex);
        }
    }

}
