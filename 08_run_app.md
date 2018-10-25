## Deploy the Pizza Store App

#### Step 1: create a manifest.yml for deploying the app on to PCF. We need to bind this app to PCC created in Exericese 01.

```
---
applications:
- name: payment-services-api-client
  random-route: true
  path: target/payment-services-api-client-0.0.1-SNAPSHOT.jar
  services:
  - workshop-pcc
```

#### Step 2: Deploy the app on PCF

```
cf push
```

## Run the Payment Services App

#### Payment App Authorize: 

http://payment-services-api-client.xyz.cf-app.com/authorize

```
curl -X POST -d @authorize.txt http://payment-services-api-client.xyz.cf-app.com/authorize --header "Content-Type: application/json
```

```
{
card: {
number: "1234",
expiryMonth: "9",
expiryYear: "2018",
cvc: "456",
holderName: "Li Wang"
},
amount: {
value: 3000,
currency: "EUR"
},
reference: "1234E",
merchantAccount: "MASTER CARD"
}
```

#### Payment App Capture: 

http://payment-services-api-client.xyz.cf-app.com/capture

```
curl -X POST -d @capture-payment.txt http://pcc-inline-caching-client-rested-lynx.apps.chowchilla.cf-app.com/capture --header "Content-Type: application/json"
```

```
{
originalReference: "PSPR-1011",
modificationAmount: {
value: 500,
currency: "EUR"
},
reference: "YMR-1011",
merchantAccount: "MASTER CARD"
}
```