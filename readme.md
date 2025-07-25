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

🔄 Sample Auth URL Generator
Here's a working version you can paste and modify in your browser:

```bash
https://accounts.google.com/o/oauth2/v2/auth?client_id=YOUR_CLIENT_ID&redirect_uri=http://localhost:8080/auth/google/callback&response_type=code&scope=email%20profile&access_type=offline&prompt=consent
clientId: 203386475544-334vvfbcf7faq2o9ul3q2go5vcc6adt8.apps.googleusercontent.com
i.e
https://accounts.google.com/o/oauth2/v2/auth?client_id=203386475544-334vvfbcf7faq2o9ul3q2go5vcc6adt8.apps.googleusercontent.com&redirect_uri=http://localhost:8080/login/oauth2/code/google&response_type=code&scope=email%20profile&access_type=offline&prompt=consent
```

https://accounts.google.com/o/oauth2/v2/auth?
client_id=203386475544-334vvfbcf7faq2o9ul3q2go5vcc6adt8.apps.googleusercontent.com
&redirect_uri=http://localhost:8080/login/oauth2/code/google
&response_type=code
&scope=openid%20email%20profile
&access_type=offline
&prompt=consent


### Store Procedure Example
```sql
CREATE PROCEDURE GetUserByEmail(IN userEmail VARCHAR(255))
BEGIN
    IF EXISTS (SELECT 1 FROM users WHERE email = userEmail) THEN
        SELECT id, name, email FROM users WHERE email = userEmail;
    ELSE
        SELECT 'User not found' AS message;
    END IF;
END;

```

### Explanation
- `CREATE PROCEDURE GetUserByEmail(IN userEmail VARCHAR(255))`: This defines a stored procedure named `GetUserByEmail` that takes one input parameter `userEmail` of type `VARCHAR(255)`.
- `BEGIN ... END;`: This block contains the SQL statements that make up the body of the procedure.
- `SELECT * FROM users WHERE email = userEmail;`: This SQL statement retrieves all columns from the `users` table where the `email` matches the input parameter `userEmail`.
- To call this stored procedure, you would use:
```sql
CALL GetUserByEmail('example@example.com');
```


SP for get all blogs
```sql
CREATE PROCEDURE GetAllBlogs()
BEGIN
    SELECT * FROM blogs;
END;
```

Then to use the store procedure in your repository layer, you can call it like this:

```java
@Procedure(name = "GetAllBlogs")
public List<Blog> getAllBlogs();
```

SP FOR
```java
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Procedure(procedureName = "findBlogsByAuthor")
    List<Blog> findBlogsByAuthor(@Param("authorName") String authorName);
}
```

```sql
CREATE PROCEDURE findBlogsByAuthor(IN authorName VARCHAR(255))
BEGIN
    SELECT * FROM blogs WHERE author = authorName;
END;
```

#### Web socket
![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)


### Circuit breaker
We have a circuit breaker in our application to handle failures gracefully. It prevents the system from making calls to a service that is likely to fail, allowing it to recover and avoid cascading failures.
We have three states for the circuit breaker:
1. **Closed**: The circuit breaker allows requests to pass through to the service.
2. **Open**: The circuit breaker blocks requests to the service, preventing further calls until it is reset.
3. **Half-Open**: The circuit breaker allows a limited number of requests to pass through to test if the service has recovered.
4. **Reset**: The circuit breaker resets to the Closed state after a successful request in Half-Open state.

To use, add the resilience4j-spring-boot3 dependency to your project. 
```xml
   <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

<dependency>
<groupId>io.github.resilience4j</groupId>
<artifactId>resilience4j-spring-boot3</artifactId>
<version>2.1.0</version>
</dependency>
```


#### Enable and disable the togglz
```json
POST http://localhost:8080/actuator/togglz
{
    "name":"DISCOUNT_APPLIED",
    "enabled": true
}
```
![img_3.png](img_3.png)

### You can also enable and disable togglz using the UI
http://localhost:8080/togglz-console/index
![img_4.png](img_4.png)