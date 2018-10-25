# pcc-inline-caching-demo

This demo is used to show how inline cache pattern is used in Pivotal Cloud Cache and MongoDB.

## Built With

* `pcc-inline-caching-client` - sample client that provides rest APIs to interact with PCC.
* `pcc-inline-caching-server` - deployed on PCC server side to enable write-behind. 

## Installing

### Step 1 - Connect GFSH with PCC Cluster

```
gfsh> connect --use-http=true --url=<pcc-gfsh-url> --user=<username> --password=<password>
```

### Step 2 - Deploy Server Jar using GFSH

```
gfsh> deploy --jar=<caching-server-jar-path>

gfsh> configure pdx --auto-serializable-classes=.* --disk-store=DEFAULT
```
### Step 3 - Reboot PCC Servers

```
$ cf update-service <your-pcc-service-name> -c '{"restart": true}'
```
### Step 4 - Recreate Region and AsyncQueue

```


gfsh> create region --name=item --type=REPLICATE --async-event-queue-id=item-writebehind-queue

gfsh> create async-event-queue --listener=io.pivotal.event.writebehind.ItemAsyncEventListener --id=item-writebehind-queue --batch-size=10 --batch-time-interval="20" --parallel="false" --dispatcher-threads=1 --listener-param=mongoAddress#xx.xx.xx.xx,mongoPort#27017,dbName#test,collectionName#customers
```

### Step 5 - Deploy `pcc-inline-caching-client` on Pivotal Cloud Foundry

Modify `manifest.yaml` and `application.properties` for your environment. Compile `pcc-inline-caching-client` and `cf push` into your PCF. Make sure it is binded with PCC.

## Use Demo

The client app has 3 rest endpoints to use:

* `/authorize` - accept authorize POST JSON data and save into PCC. `CREATE` event will be captured and async saved into remote MongoDB server.
* `/capture` - accept capture POST JSON data and save into PCC. `CREATE` event will be captured and async saved into remote MongoDB server.
* `/search?reference=` - look for data by `reference` field. If not found in PCC, load from MongoDB.
* `/clear` - clean PCC region
