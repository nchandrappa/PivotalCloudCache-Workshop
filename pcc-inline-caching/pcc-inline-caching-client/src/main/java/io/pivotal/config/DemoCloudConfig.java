package io.pivotal.config;

import java.net.URI;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class DemoCloudConfig extends AbstractCloudConfig {

    private static final String SECURITY_CLIENT = "security-client-auth-init";
    private static final String SECURITY_USERNAME = "security-username";
    private static final String SECURITY_PASSWORD = "security-password";
	
	@Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache() throws Exception {		
		
		ClientCacheFactory factory = new ClientCacheFactory();
		for (URI locator : EnvParser.getInstance().getLocators()) {
            factory.addPoolLocator(locator.getHost(), locator.getPort());
        }
		
		factory.set(SECURITY_CLIENT, "io.pivotal.config.ClientAuthInitialize.create");
        factory.set(SECURITY_USERNAME, EnvParser.getInstance().getUsername());
        factory.set(SECURITY_PASSWORD, EnvParser.getInstance().getPassword());
        factory.setPdxSerializer(new ReflectionBasedAutoSerializer(".*"));
        factory.setPdxReadSerialized(false);
        factory.setPoolSubscriptionEnabled(true); // to enable CQ
        
        return factory.create();
    }
	
	@Bean(name = "item")
	public Region<String, PdxInstance> itemRegion(@Autowired ClientCache clientCache) {
		ClientRegionFactory<String, PdxInstance> itemRegionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);

		Region<String, PdxInstance> itemRegion = itemRegionFactory.create("item");

		return itemRegion;
	}

}
