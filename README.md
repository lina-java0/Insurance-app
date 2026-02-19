Insurance App — backend-приложение для расчета страховой премии по туристической страховке.

Архитектура: Hexagonal Architecture (Ports and Adapters).
Бизнес-логика полностью сосредоточена в core.
REST и RabbitMQ реализованы как inbound и outbound адаптеры.

Tech Stack:
Java 21
Spring Boot 3
Spring Web
Spring Data JPA
RabbitMQ
Liquibase
H2
MySQL
Gradle

Дополнительно:
Настроены Spring Profiles (H2 / MySQL)
Используется Liquibase для миграций БД
Реализованы интеграционные тесты

Проект находится в активной разработке.
