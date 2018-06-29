package org.banyan.gateway.helios.data.mongo.configuration;

import com.mongodb.MongoClientOptions;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MongoClientConfiguration
 * Mongo Client Configuration
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月21日 15:53:00
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.client")
@AutoConfigureBefore({ MongoAutoConfiguration.class })
public class MongoClientConfiguration {
    private int minConnectionsPerHost = 5;
    private int maxConnectionsPerHost = 100;
    private int threadsAllowedToBlockForConnectionMultiplier = 5;
    private int serverSelectionTimeout = 1000 * 30;
    private int maxWaitTime = 1000 * 60 * 2;
    private int maxConnectionIdleTime;
    private int maxConnectionLifeTime;
    private int connectTimeout = 1000 * 10;
    private int socketTimeout = 0;
    private boolean socketKeepAlive = false;
    private boolean sslEnabled = false;
    private boolean sslInvalidHostNameAllowed = false;
    private boolean alwaysUseMBeans = false;

    private int heartbeatFrequency = 10000;
    private int minHeartbeatFrequency = 500;
    private int heartbeatConnectTimeout = 20000;
    private int heartbeatSocketTimeout = 20000;
    private int localThreshold = 15;

    private String requiredReplicaSetName;

    @Bean
    public MongoClientOptions getMongoClientOptions() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.minConnectionsPerHost(minConnectionsPerHost)
                .connectionsPerHost(maxConnectionsPerHost)
                .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
                .serverSelectionTimeout(serverSelectionTimeout)
                .maxWaitTime(maxWaitTime)
                .maxConnectionIdleTime(maxConnectionIdleTime)
                .maxConnectionLifeTime(maxConnectionLifeTime)
                .connectTimeout(connectTimeout)
                .socketTimeout(socketTimeout)
                .socketKeepAlive(socketKeepAlive)
                .sslEnabled(sslEnabled)
                .sslInvalidHostNameAllowed(sslInvalidHostNameAllowed)
                .alwaysUseMBeans(alwaysUseMBeans)

                .heartbeatFrequency(heartbeatFrequency)
                .minHeartbeatFrequency(minHeartbeatFrequency)
                .heartbeatConnectTimeout(heartbeatConnectTimeout)
                .heartbeatSocketTimeout(heartbeatSocketTimeout)
                .localThreshold(localThreshold)

                .requiredReplicaSetName(requiredReplicaSetName);

        return builder.build();
    }

    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public int getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(int serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public boolean isSslInvalidHostNameAllowed() {
        return sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(boolean sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public boolean isAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public int getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(int heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public int getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(int minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public int getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(int heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public int getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(int heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public int getLocalThreshold() {
        return localThreshold;
    }

    public void setLocalThreshold(int localThreshold) {
        this.localThreshold = localThreshold;
    }

    public String getRequiredReplicaSetName() {
        return requiredReplicaSetName;
    }

    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }
}
