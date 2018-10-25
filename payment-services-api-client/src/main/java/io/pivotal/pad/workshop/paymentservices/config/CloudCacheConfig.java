package io.pivotal.pad.workshop.paymentservices.config;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLogging;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.geode.config.annotation.UseMemberName;

import io.pivotal.pad.workshop.paymentservices.domain.TransactionRules;
import io.pivotal.pad.workshop.paymentservices.repo.TransactionRulesRepo;

@EnableEntityDefinedRegions(basePackageClasses = TransactionRules.class)
@EnableGemfireRepositories(basePackageClasses = TransactionRulesRepo.class)
@EnableLogging(logLevel = "config")
@UseMemberName(value = "PaymentServicesApiClient")
@Configuration
public class CloudCacheConfig {
	
	
	@Bean(name = "transactionsRegion")
	@DependsOn({"gemfireCache"})
	public Region<String, PdxInstance> transactionsRegion(@Autowired ClientCache clientCache) {
		ClientRegionFactory<String, PdxInstance> transactionsRegionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
		Region<String, PdxInstance> transactionsRegion = transactionsRegionFactory.create("Transactions");
		return transactionsRegion;
	}

}
