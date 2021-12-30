package com.example.springcachedemo.configuration;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.aerospike.cache.AerospikeCacheConfiguration;
import org.springframework.data.aerospike.cache.AerospikeCacheManager;
import org.springframework.data.aerospike.convert.AerospikeCustomConversions;
import org.springframework.data.aerospike.convert.AerospikeTypeAliasAccessor;
import org.springframework.data.aerospike.convert.MappingAerospikeConverter;
import org.springframework.data.aerospike.mapping.AerospikeMappingContext;
import org.springframework.data.convert.CustomConversions;

import static java.util.Collections.emptyList;

// @Configuration
@Import(value = {AerospikeMappingContext.class, AerospikeTypeAliasAccessor.class})
@RequiredArgsConstructor
public class AerospikeConfig {

    @Value("${aerospike.host}")
    private String host;
    @Value("${aerospike.port}")
    private int port;

    @Bean(destroyMethod = "close")
    public AerospikeClient aerospikeClient() {
        ClientPolicy clientPolicy = new ClientPolicy();
        clientPolicy.failIfNotConnected = true;
        return new AerospikeClient(clientPolicy, host, port);
    }

    @Bean(name = "aerospikeCacheManager")
    public CacheManager level2Cache(AerospikeClient aerospikeClient, @Qualifier("customConverter") MappingAerospikeConverter converter) {
        AerospikeCacheConfiguration defaultConfiguration = new AerospikeCacheConfiguration("test");
        return new AerospikeCacheManager(aerospikeClient, converter, defaultConfiguration);
    }

    @Bean
    @Qualifier("customConverter")
    public MappingAerospikeConverter converter(AerospikeMappingContext mappingContext, AerospikeTypeAliasAccessor aerospikeTypeAliasAccessor) {
        CustomConversions conversions = new AerospikeCustomConversions(emptyList());
        return new MappingAerospikeConverter(
                mappingContext, conversions, aerospikeTypeAliasAccessor
        );
    }
}
