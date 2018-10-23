## PivotalCloudCache-Workshop

This workshop will provide developers with hands on experience in building Pivotal Cloud Cache(PCC) clients using Spring Data GemFire (SDG), Spring Data REST, Spring Cloud and Spring Boot. In this session we'll be implementing a pizza store app for ordering pizza backed by PCC. Session includes presentations, demos and hands on labs.

![](images/look_aside.png)

Agenda:

* Session 1: Pivotal Cloud Cache (PCC) Essentials
** No lab
* Session 2: Provisioning PCC Cluster and Developer Access
** Lab 01 - Installation & Developer setup
* Session 3:  Pivotal Cloud Cache: Essentials:   <<<. Did you realize that session 1 & 3 have the same name but different topics?  I think we should rename this as Pivotal Cloud Cache Architecture Essentials as it covers Topology 
** No Lab
* Session 4: Pivotal Cloud Cache: Access Patterns
** Lab 02 - Accessing PCC
* Session 5: Effective Querying in PCC
** Lab 03 - Query PCC
* Session 6: Server Side Jar Deployments
** Lab 04 - deploying server side jar (Li Wang’s demo)
* Session 7: Pivotal Cloud Cache Design Patterns
** Lab 05 - Look aside, in-line caching, shared cache, etc. (most is already done)
* Session 8: Pivotal Cloud Cache Active-Active WAN
** Lab 06 - wan replication
* Session 9: Next Best Offer - Conceptual Demo
** No lab - walk thru

Demo App: 

http://pizza-store-pcc-client.xyz.numerounocloud.com/pizzas

```
Lets Order Some Pizza 
-------------------------------
types: plain, fancy

GET /orderPizza?email={emailId}&type={pizzaType} - Order a pizza 
GET /orders?email={emailId} - get specific value 

```

http://pizza-store-pcc-client.xyz.numerounocloud.com/orderPizza?email=lucynorton@gmail.com&type=fancy

###### Result

Cache Miss Scenario

```
Result [Pizza{name='fancy', toppings=[arugula, chicken], sauce='pesto', Customer='Customer [id=05eKpgOFA, name=Lucy Norton, email=lucynorton@gmail.com, address=48665 Washington, birthday=1965-02-10T06:20:27.828Z]'}] 
Cache Miss for Customer [true] 
Read from [MYSQL] 
Elapsed Time [234 ms]
```

Data Returned From Cache 
```
Result [Pizza{name='fancy', toppings=[arugula, chicken], sauce='pesto', Customer='Customer [id=05eKpgOFA, name=Lucy Norton, email=lucynorton@gmail.com, address=48665 Washington, birthday=1965-02-10T06:20:27.828Z]'}] 
Cache Miss for Customer [false] 
Read from [PCC] 
Elapsed Time [2 ms]
```
