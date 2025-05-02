### XML Header
```xml
<?xml version="1.0" encoding="UTF-8"?>
```
- This is the XML declaration — it tells the parser this file is XML and uses UTF-8 character encoding.

### Root <beans> Tag with Namespaces
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
```
```txt
This sets up:

The XML namespace used by Spring (xmlns)

The XML Schema instance (xsi) namespace for schema validation

Where to find the schema file for validating the structure (xsi:schemaLocation)

This is required for Spring to understand how to process and validate the XML file.
```

### Bean Definition
```xml
<bean id="transactionLimits" class="com.drsimple.jwtsecurity.wallet.TransactionLimits">
```
- This tells Spring to create and manage a bean of type TransactionLimits, with the ID "transactionLimits".
- The class is:
```txt
com.drsimple.jwtsecurity.wallet.TransactionLimits
```

### Setting Properties
```xml
    <property name="withdrawalEnabled" value="true"/>
<property name="depositEnabled" value="true"/>
<property name="maxDailyWithdrawal" value="1000"/>
<property name="maxDepositAmount" value="5000"/>
```
These set the values of properties (via setters) on the TransactionLimits object that Spring will create.
It's equivalent to:

```java
TransactionLimits limits = new TransactionLimits();
limits.setWithdrawalEnabled(true);
limits.setDepositEnabled(true);
limits.setMaxDailyWithdrawal(1000);
limits.setMaxDepositAmount(5000);

```

### Closing </bean> and </beans>
- That wraps up the configuration of the TransactionLimits bean and the root <beans> block.

### How to Use in Spring
- To make this XML effective, your app must import this file using @ImportResource, like:
```java
@Configuration
@ImportResource("classpath:transaction-limits-config.xml")
public class AppConfig {}
```
This tells Spring to read and register beans from the XML file into the application context.

#### Final code
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- XML declaration specifying UTF-8 encoding -->

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       
    <!-- Root element for Spring Bean definitions.
         - xmlns: Defines the default namespace for Spring beans.
         - xsi: Used for XML Schema validation.
         - xsi:schemaLocation: Tells the XML parser where to find the Spring beans schema.
    -->

    <!-- Define a Spring bean named 'transactionLimits' -->
    <bean id="transactionLimits" class="com.drsimple.jwtsecurity.wallet.TransactionLimits">
        
        <!-- Set the property 'withdrawalEnabled' to true -->
        <property name="withdrawalEnabled" value="true"/>

        <!-- Set the property 'depositEnabled' to true -->
        <property name="depositEnabled" value="true"/>

        <!-- Set the property 'maxDailyWithdrawal' to 1000 -->
        <property name="maxDailyWithdrawal" value="1000"/>

        <!-- Set the property 'maxDepositAmount' to 5000 -->
        <property name="maxDepositAmount" value="5000"/>

    </bean>

</beans>
```