# Elytron demo for Devconf 2018

This is demo application for my Elytron presentation on Devconf 2018.
It is supposed to work on WildFly 11.

## Install and configure WildFly
1. Download Full WildFly 11: http://wildfly.org/downloads/
2. Copy enclosed standalone.xml into `standalone/configuration` of your downloaded WildFly
3. Copy demo properties realm files `wildfly-users.properties` and `wildfly-roles.properties` into `standalone/configuration` too
4. Start downloaded wilfly: `bin/standalone.sh`

## Deploy demo application
```
cd ejb-backend; mvn install; cd ..
cd web-frontend; mvn install; cd ..
cd ear; mvn wildfly:deploy; cd ..
```

## Access web application
http://localhost:8080/web-frontend/

| Username            | Password |
|---------------------|----------|
| admin@wildfly.org   | admin    |
| adder@wildfly.org   | adder    |
| remover@wildfly.org | remover  |

## Run demo client application
```
cd client-app
mvn exec:java
```

