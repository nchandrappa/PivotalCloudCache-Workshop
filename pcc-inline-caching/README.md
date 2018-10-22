# pcc-inline-caching-demo

This demo is used to show how inline cache pattern is used in Pivotal Cloud Cache and MongoDB.

## Built With

* `pcc-inline-caching-client` - sample client that provides rest APIs to interact with PCC.
* `pcc-inline-caching-server` - deployed on PCC server side to enable write-behind and read-through. 

## Installing

### Step 1 - Connect GFSH with PCC Cluster

```
gfsh> connect --use-http=true --url=<pcc-gfsh-url> --user=<username> --password=<password>
```

### Step 2 - Compile `pcc-inline-caching-server`

Update [MongoConnection](https://github.com/liwang-pivotal/pcc-inline-caching-demo/blob/master/pcc-inline-caching-server/src/main/java/io/pivotal/util/MongoConnection.java) class for your remote MongoDB server.

### Step 3 - Deploy Server Jar using GFSH

```
gfsh> deploy --jar=<caching-server-jar-path>

gfsh> create region --name=item --type=PARTITION_PERSISTENT --async-event-queue-id=item-writebehind-queue --cache-loader=io.pivotal.event.readthrough.ItemCacheLoader

gfsh> create async-event-queue --listener=io.pivotal.event.writebehind.ItemAsyncEventListener --id=item-writebehind-queue --batch-size=10 --batch-time-interval="20" --parallel="false" --dispatcher-threads=1

gfsh> configure pdx --auto-serializable-classes=.* --disk-store=DEFAULT
```
### Step 4 - Reboot PCC Servers

```
$ cf update-service <your-pcc-service-name> -c '{"restart": true}'
```

### Step 5 - Deploy `pcc-inline-caching-client` on Pivotal Cloud Foundry

Compile `pcc-inline-caching-client` and `cf push` into your PCF. Make sure it is binded with PCC.

## Use Demo

The client app has 3 rest endpoints to use:

* `/payment` - accept POST JSON data and save into PCC. `CREATE` event will be captured and async saved into remote MongoDB server.
* `/search?reference=` - look for data by `reference` field. If not found in PCC, load from MongoDB.
* `/clear` - clean PCC region
