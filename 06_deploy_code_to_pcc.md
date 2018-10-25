## Deploy ServerSide code to PCC


### Step 1 - Connect GFSH with PCC Cluster

```
gfsh> connect --use-http=true --url=<pcc-gfsh-url> --user=<username> --password=<password>
```

### Step 2 - Deploy Server Jar using GFSH

```
gfsh> deploy --jar=<caching-server-jar-path>
```

#### Step 3: Reboot PCC Servers

```
cf update-service <your-pcc-service-name> -c '{"restart": true}'
```

#### Step 4: Create AsyncEvent queue and Configure the region with AsyncEvent

```
gfsh> create async-event-queue --listener=io.pivotal.event.writebehind.ItemAsyncEventListener --id=item-writebehind-queue --batch-size=10 --batch-time-interval="20" --parallel="false" --dispatcher-threads=1 --listener-param=mongoAddress#xx.xx.xx.xx,mongoPort#27017,dbName#test,collectionName#customers

gfsh> create region --name=Transactions --type=PARTITION_PERSISTENT --async-event-queue-id=item-writebehind-queue
```