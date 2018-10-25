## Pizza Store App

Let's incrementally build Payment Transactions API which will showcase various features supported by PCC and Spring Data

#### Step 1: Create a spring boot starter project from https://start.spring.io. Add spring-boot-starter-web dependency.

#### Step 2: Create Skeleton PCC Client project

a. Configure pom.xml with PCC Dependencies. For convenience we have provided all the required dependies for Mock Payment Services App.

###### Pivotal Cloud Cache Dependency

```
<dependency>
	<groupId>org.springframework.geode</groupId>
	<artifactId>spring-gemfire-starter</artifactId>
	<version>1.0.0.M3</version>
</dependency>

```

###### Payment Services App dependenices

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>


<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.geode</groupId>
    <artifactId>spring-gemfire-starter</artifactId>
    <version>1.0.0.M3</version>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
</dependency>

<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jcl-over-slf4j</artifactId>
</dependency>

<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jul-to-slf4j</artifactId>
</dependency>

```



#### Step 2: configure PCC client with Spring Data GemFire(SDG) annotations

a. Create a configuration file which enables Auto Reconfiguration for connectiong to PCC Instance. All the boilerplate code for parsing VCAP Services and creation of GemFire ClientCache is automagically created by having spring-gemfire-starter project on the call path.

```
@EnableLogging(logLevel = "config")
@UseMemberName(value = "PaymentServicesApiClient")
@Configuration
public class CloudCacheConfig {

}

```
