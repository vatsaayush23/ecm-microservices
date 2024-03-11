E-commerce Microservices Project - Spring Boot

• Product Service: Manages product information and utilizes MongoDB as the NoSQL database for storage.
• Order Service: Handles order placement, stores order data in a SQL database. Synchronously communicates with
the Inventory Service using WebClient from Spring Web Flux to check product availability. A Circuit-Breaker
logic is implemented with Inventory service using Resilience4j for fault tolerance. Asynchronously communicates
with Notification Service upon order placement using Kafka.
• Inventory Service: Manages inventory and product quantities with a dedicated SQL database for storage.
• Notification Service: Consumes order-placed events from Kafka and sends notifications to users.
• Discovery Server: Utilizes Netflix Eureka for service discovery.
• API Gateway: Implemented using Spring Cloud Reactive Gateway, secured by OAuth2 using Keycloak, and
acts as the primary entry point for users with a valid access token.
• Distributed Tracing: Implemented using Zipkin.
• Containerization: The entire application is containerized using Docker.
• Monitoring: Monitoring is achieved using Prometheus, collecting data from the application and visualizing it
through Grafana dashboards.
