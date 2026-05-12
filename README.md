# РЕЗУЛЬТАТИ ВИКОНАННЯ
```
PS D:\НАВЧАННЯ\Бази даних\college-schedule-app-25-postgres\college-schedule-app-25-postgres> mvn clean compile exec:java
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< com.veterinary:college-schedule >-------------------
[INFO] Building college-sample 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ college-schedule ---
[INFO] Deleting D:\НАВЧАННЯ\Бази даних\college-schedule-app-25-postgres\college-schedule-app-25-postgres\target
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ college-schedule ---
[INFO] Copying 2 resources from src\main\resources to target\classes
[INFO]
[INFO] --- compiler:3.13.0:compile (default-compile) @ college-schedule ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 6 source files with javac [debug target 1.8] to target\classes
[WARNING] bootstrap class path is not set in conjunction with -source 8
  not setting the bootstrap class path may lead to class files that cannot run on JDK 8
    --release 8 is recommended instead of -source 8 -target 1.8 because it sets the bootstrap class path automatically
[WARNING] source value 8 is obsolete and will be removed in a future release
[WARNING] target value 8 is obsolete and will be removed in a future release
[WARNING] To suppress warnings about obsolete options, use -Xlint:-options.
[INFO]
[INFO] --- exec:3.1.0:java (default-cli) @ college-schedule ---
груд. 11, 2025 8:20:53 ПП org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 5.6.14.Final
груд. 11, 2025 8:20:53 ПП org.hibernate.boot.jaxb.internal.stax.LocalXmlResourceResolver resolveEntity
WARN: HHH90000012: Recognized obsolete hibernate namespace http://hibernate.sourceforge.net/hibernate-configuration. Use namespace http://www.hibernate.org/dtd/hibernate-configuration instead.  Support for obsolete DTD/XSD namespaces may be removed at any time.
груд. 11, 2025 8:20:53 ПП org.hibernate.annotations.common.reflection.java.JavaReflectionManager <clinit>
INFO: HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator instantiateC3p0Provider
WARN: HHH000022: c3p0 properties were encountered, but the c3p0 provider class was not found on the classpath; these properties are going to be ignored.
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
WARN: HHH10001002: Using Hibernate built-in connection pool (not for production use!)
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
INFO: HHH10001005: using driver [org.postgresql.Driver] at URL [jdbc:postgresql://localhost:5432/vet_clinik]
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
INFO: HHH10001001: Connection properties: {user=postgres, password=****}
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
INFO: HHH10001003: Autocommit mode: false
груд. 11, 2025 8:20:53 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PooledConnections <init>
INFO: HHH000115: Hibernate connection pool size: 20 (min=1)
груд. 11, 2025 8:20:53 ПП org.hibernate.dialect.Dialect <init>
INFO: HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
Hibernate: 

    alter table appointment
       drop constraint FK8y0it8yrd322ps2jklm5f8e07
груд. 11, 2025 8:20:54 ПП org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@5b66d64] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
Hibernate:

    alter table appointment
       drop constraint FK97ym201tvtxg3slhf3khn36ye
Hibernate: 

    alter table pet
       drop constraint FK7qfti9yba86tgfe9oobeqxfxg
Hibernate:

    drop table if exists appointment cascade
Hibernate:

    drop table if exists owner cascade
Hibernate: 

    drop table if exists pet cascade
Hibernate:

    drop table if exists veterinarian cascade
Hibernate: 

    create table appointment (
       appointment_id  serial not null,
        appointment_date date not null,
        appointment_time time not null,
        diagnosis_treatment varchar(255) not null,
        is_vaccinated boolean not null,
        pet_id int4 not null,
        vet_id int4 not null,
        primary key (appointment_id)
    )
груд. 11, 2025 8:20:54 ПП org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@62cbe38c] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
Hibernate: 

    create table owner (
       owner_id  serial not null,
        apartment varchar(255),
        city varchar(255),
        first_name varchar(255),
        house varchar(255),
        last_name varchar(255),
        middle_name varchar(255),
        phone varchar(255),
        street varchar(255),
        primary key (owner_id)
    )
Hibernate: 

    create table pet (
       pet_id  serial not null,
        name varchar(255) not null,
        species varchar(255),
        owner_id int4 not null,
        primary key (pet_id)
    )
Hibernate: 

    create table veterinarian (
       vet_id  serial not null,
        experience_years int4,
        full_name varchar(255) not null,
        primary key (vet_id)
    )
Hibernate:

    alter table owner
       add constraint UK_q3nfx0w9lmfnyeepg23ccenhu unique (phone)
Hibernate: 

    alter table appointment
       add constraint FK8y0it8yrd322ps2jklm5f8e07
       foreign key (pet_id)
       references pet
Hibernate:

    alter table appointment
       add constraint FK97ym201tvtxg3slhf3khn36ye
       foreign key (vet_id)
       references veterinarian
Hibernate:

    alter table pet
       add constraint FK7qfti9yba86tgfe9oobeqxfxg
       foreign key (owner_id)
       references owner
>>> Програма 'Ветеринарна кл?н?ка' запущена.

-------------------------------------------
 МЕНЮ УПРАВЛ?ННЯ:
 1. Наповнити базу тестовими даними
 2. Переглянути зв?т прийом?в
 3. Видалити вс? дан?
 4. Вих?д
-------------------------------------------
Ваш виб?р: 4
>>> Завершення роботи...
груд. 11, 2025 8:22:00 ПП org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PoolState stop
INFO: HHH10001008: Cleaning up connection pool [jdbc:postgresql://localhost:5432/vet_clinik]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:09 min
[INFO] Finished at: 2025-12-11T20:22:00+02:00
[INFO] ------------------------------------------------------------------------
```