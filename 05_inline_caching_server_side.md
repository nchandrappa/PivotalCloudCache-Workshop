## pcc-inline-caching-demo

This demo is used to show how inline cache pattern is used with Pivotal Cloud Cache to sync region data into MongoDB.

## Built With

* `pcc-inline-caching-server` - deployed on PCC server side to enable write-behind and read-through. 

#### Step 1: Create a maven project and include the following dependencies

```
<dependency>
	<groupId>org.apache.geode</groupId>
	<artifactId>geode-core</artifactId>
	<version>9.3.0</version>
</dependency>

<dependency>
	<groupId>org.mongodb</groupId>
	<artifactId>mongo-java-driver</artifactId>
	<version>3.4.1</version>
</dependency>

```

Note: For production apps, please download the Pivotal GemFire jars by including following repository. Refer: https://gemfire.docs.pivotal.io/96/gemfire/getting_started/installation/obtain_gemfire_maven.html
```
<repository>
	<id>gemfire-release-repo</id>
	<name>Pivotal GemFire Release Repository</name>
	<url>https://commercial-repo.pivotal.io/data3/gemfire-release-repo/gemfire</url>
</repository>
```

#### Step 2: We need to create uber jar so that all the requried dependencies of the project is available on PCC server side.

```
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-shade-plugin</artifactId>
			<version>3.1.1</version>
			<executions>
				<execution>
					<phase>package</phase>
					<goals>
						<goal>shade</goal>
					</goals>
					<configuration>
						<artifactSet>
							<excludes>
								<exclude>classworlds:classworlds</exclude>
								<exclude>junit:junit</exclude>
								<exclude>jmock:*</exclude>
								<exclude>*:xml-apis</exclude>
								<exclude>org.apache.maven:lib:tests</exclude>
								<exclude>log4j:log4j:jar:</exclude>
							</excludes>
						</artifactSet>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

#### Step 3: Implement AsyncListener Interface and retrieve all the connection information from init method

```
public void init(Properties props) {
	String mongoAddress = props.getProperty("mongoAddress");
	String mongoPort = props.getProperty("mongoPort");
	String dbName = props.getProperty("dbName");
	String collectionName = props.getProperty("collectionName");
	
	itemCollection = new MongoClient(mongoAddress, Integer.parseInt(mongoPort)).getDB(dbName).getCollection(collectionName);
}
```


#### Step 4: processEvents() method is used for handling all the entry events for implementing writebehind code in processEvents() to sync region data with MongoDB Collection. Refer: io.pivotal.event.writebehind.ItemAsyncEventListener

```
public boolean processEvents(List<AsyncEvent> events) {

		for (AsyncEvent ge : events) {
			if (ge.getOperation().equals(Operation.CREATE)) {
				PdxInstance item = (PdxInstance) ge.getDeserializedValue();

				log.info("CREATE event caught... Inserting into MongoDB now...");
				
				insertItem(JSONFormatter.toJSON(item));
			}
			// SKIP "UPDATE" AND "DELETE" EVENT FOR NOW
		}
		
		return true;
	}
```

#### Step 5: Build the jar

```
mvn clean package
```