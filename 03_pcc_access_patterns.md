## Pivotal CloudCache Access patterns

Lets implement CRUD operations against PCC Region using Spring Data Repository.

#### Step 1: Create Domain object for Mock Reference Data TransactionRules. Get the domain objects from payment-services-api-client project

#### Step 2: Implement GemFire repositories for PizzaOrder and Customer domain objects.

```
package io.pivotal.data.repo;

@RepositoryRestResource(path = "transactionrules")
public interface TransactionRulesRepo extends GemfireRepository<TransactionRules, String> {

}

```

#### Step 3: configuring spring boot app with REST repositories

@EnableGemfireRepositories annotation configures the client to create Spring Data GemFire repositories for all the domain objects annotated with @Region

```
@EnableEntityDefinedRegions(basePackageClasses = TransactionRules.class)
@EnableGemfireRepositories(basePackageClasses = TransactionRulesRepo.class)
@EnableLogging(logLevel = "config")
@UseMemberName(value = "PaymentServicesApiClient")
@Configuration
public class CloudCacheConfig {

}
```

#### Step 4: Implement Loader for reference data

Please refer to io.pivotal.pad.workshop.paymentservices.controller.ReferenceDataController

#### Step 5: Deploy the App

```
---
applications:
- name: payment-services-api-client
  random-route: true
  path: target/payment-services-api-client-0.0.1-SNAPSHOT.jar
  services:
  - workshop-pcc
```

```
cf push
```

#### Step 6: load reference data

```
https://payment-services-api-client.xyz.numerounocloud.com/referencedata/load
```

#### Step 7: Retrieve Reference Data

```
https://payment-services-api-client.xyz.numerounocloud.com/transactionrules
```


