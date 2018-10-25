## Preparing PCC Cluster for ServerSide code deployment

#### Step 1: Connect GFSH with PCC Cluster

```
gfsh> connect --use-http=true --url=<pcc-gfsh-url> --user=<username> --password=<password>
```

#### Step 2: Configure PDX Serializer to use Reflection Based Serializer

```
gfsh> configure pdx --auto-serializable-classes=.* --disk-store=DEFAULT
```
