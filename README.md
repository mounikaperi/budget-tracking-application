# budget-tracking-application
Spring Microservices Backend project that holds account holder account, transactions, profile and statements information

Pre-Requisites

    Spring Framework
     
        - Spring Core, Spring Context
        - Spring EL
        - Spring Boot
        - Spring Web (Web MVC) & REST API
        - Spring Data JPA

Project SetUp:

    JDK 1.8
    STS 4
    Maven
    MySQL Community Server 8.0

Introduction

    MonoLithical

        1. The entire application is in one deployment-unit.
        2. The entire application is modularized through layers.
        3. Layer are going offer loosly couple components.

        - Load Management via Scaling.
        - Interoparability
        - Management 
    
    Microservices

        1. A microservice is one isolated module of an application that is
            independetly managed as a deployment unit.
        2. A group of inter-communicating microservices form an application eco-system.

        + Load Management via Scaling.
        + Interoparability
        + Management 

        ? Decomposition
        ? Inter Communication
        ? Distributed Tracing
        ? Fault Tolerence (Fall Backs)
        ? Monitoring and Configuration

    Microservices Design Patterns

        Decomposition Design Patterns
            Decomposition by Domain
            Decomposition by Sub-domain
        Integration Design Patterns
            Api Gateway Pattern
            Aggregator Pattern
            Client Side Component Pattern
        Database Design Patterns
            Database Per Service
            Shared Database            
            Saga Pattern
            CQRS Pattern
        Observability Design Patterns
            Log Aggregator
            Performence Metrics Aggregator
            Distributed Tracing
        Cross Cutting Design Patterns
            Discovery Service 
            Circuite Breaker
            External Configuaration

Budget Tracking Application Case Study:

    1. We need to have different consumer or account holders to register
    2. Each accountHolder must be able to record his spending or earning transactions
    3. Generate a statement periodically displaying the total spending, the total earning and balance

Decomposition by Domain:

    1. profile-service: allows consumers or accountHolders to register
    2. transaction-service: allows the consumer or accountHolder to insert/update/delete the transactions
    3. statement-service: generates the periodic statement

Sub-domain pattern guides through bounded-context:

    1. We will decompose the budgetTrackingApp into 3 microservices
     1.a) profiles-service:
           AccountHolder
             accountHolderId
             fullName
             mobileNumber
             mailId
             userId
             password
     1.b) transactions-service
           AccountHolder
             accountHolderId
             txns: Set<Txn>
           Txn
             dateOfTxn
             txnId
             txnAmount
             txnType
             owner: AccountHolder
     1.c) Statement-service
           AccountHolder
             accountHolderId
             fullName
             mobileNumber
             mailId
           Txn
             dateOfTxn
             txnId
             txnAmount
             txnType
           Statement
             owner: AccountHolder
             txns: Set<txns>
             startDate: Date
             endDate: Date
             totalSpending
             totalEarning
             balance
   
   Shared Database Pattern:
   
       Having a single DB for all microservices in brown field apps

   Database per Service Pattern:
   
       Each microservice has its own database in all green field apps

   Discovery Service Pattern:
   
     discovery-service
   
       - all microservices will register their address with discovery-service
       - the address are retrieved from here by the needy microservices

   Data Aggregation pattern
   
       - Aggregation is about designing a microservice that can collect info from other microservices analyze and aggregate the data and pass the aggregated data 
         to the client, sacing the client from making multiple requests for different parts of the data.

   Client Side Components Pattern:
   
       Each component of the UI/UX application can place their individual requests to different microservices parallely and should be receiving the responses as 
       well parallely

    Distributed Tracing Design Pattern:
   
       Tracing Service:
   
           Whenever a request comes to any of the microservices in our app-ecosystem, that request is given a uniqueId and is reported to the tracing service   
           every time the request goes from one to service to another service until the final respone is sent to the client. And the tracing service will record 
           all the track of this request along with performance metrics and log info attached with the request.

   Load Balancing Design Pattern
   
       Load Balancing means mapping the incoming requests to multiple instances of the same microservice based on round-robin algorithm
       Tools like Ribbon/Spring cloud load balancer etc are used to perform load balancing.

   API Gateway Design Pattern
   
        gateway-service <---------(all requests) -------------------> any-client
           -> forward that request to respective microservice
           <- received the response from the microservice
           ------------------------response-------------------------client

   Circuit Breaker Design Pattern
        circuit-breaker-threshold

            - When the first request could not reach a specific microservice due to its down time, a fallback mechanism is triggered.
            - After that the circuit is made open(broken) means that the fallback mechanism will address all the other consecutive requests targetting that 
              microservice. 
            - When a request to the same microservice is inbound after the threshold, then the circuit is half-closed means that a new attempt to reach the 
              microservice is made
            - On successful contact, the circuit is closed.
            - or if that microservice is still unavailable, the circuit continuous to be open.
            - Tools like Resiliance4j etc are for the purpose.

External Configuration Design Pattern

    - GitHub Repository consists a list of all config files for all the microservices.
    - config-service
        - Whenever a microservice has to start, it will first send a fetch request to the config-service
        - The config-service will check for the config file in the repo
        - The config file is passed to the microservice by the config-service
        - Whenever the config files are modified and pushed into the repo- the config service will automatically notify all the respective microservices and the 
          microservices will receive the updated config-file and restart themselves.


Decomposition by Domain ans Sub-Domain:

    budget-tracking-application
        profiles-service:
            AccountHolder entity(database)
                Long accountHolderId
                String fullName
                String mobile
                String mailId
        txns-service
            AccountHolder entity(database)
                Long accountHolderId
                Double currentBalance
            Txn Entity(database)
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate
                AccountHolder holder
        statement-service
            AccountHolder model(no database)
                Long accountHolderId
                String fullName
                String mobile
                String mailId
                Double currentBalance
            Txn Model(no database)
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate
            Statement Model(no database)
                LocalDate start
                LocalDate end
                AccountHolder profile
                Set<Txn> txns
                totalCredit
                totalDebit
                statementBalance

Aggregator Pattern for budget-tracking-application

    request for statement -> statement-service 
                                -> call profile service (take accountHolder Data)
                                -> call txns-service (collect list of transactions)
                                -> does the composition and computation into statement object
                                -> return the Statement object

Discovery Pattern for budget-tracking-application

    discovery-service (spring-cloud-netfix-eureka-discovery service) -> registration of urls and retrieval of urls
                                                                    -> register and retrieve profiles-service
                                                                    -> register and retrieve txns-service
                                                                    -> register and retrieve statement-service

API Gateway Pattern for budget-tracking-application

    Android/Angular/React application 
                -> API Gateway (spring-cloud-api-gateway) 
                            -> discovery-service (netfix eureka discovery service) 
                                    -> registration and retrieval of urls
                                        -> register and retrieve profiles-service
                                        -> register and retrieve txns-service
                                        -> register and retrieve statement-service


                
