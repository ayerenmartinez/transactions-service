Transactions Service
Este es un servicio desarrollado en Spring Boot que maneja las transacciones bancarias de manera reactiva utilizando MongoDB y JPA para el almacenamiento de datos. Además, el sistema incluye una lógica de validación de riesgos y control de fondos insuficientes para las transacciones.

Tecnologías utilizadas
Spring Boot 3.5.x
Spring Data MongoDB Reactive
Spring Data JPA
H2 Database (para pruebas locales)
Project Reactor (para programación reactiva)
Lombok (para reducir el código repetitivo)
@SpringBootTest y StepVerifier para pruebas unitarias
Docker para levantar la base de datos de MongoDB
Estructura del proyecto
src/main/java/com/nttdata/transactions_service/
controllers/: Controladores REST para manejar las solicitudes HTTP.

TransactionController.java: Define los endpoints para crear transacciones, listar transacciones por cuenta y transmitir transacciones en tiempo real.
dtos/: Clases DTO (Data Transfer Object) para la transferencia de datos entre capas.

CreateTxRequest.java: Define los datos necesarios para crear una transacción.
models/: Modelos de entidades utilizadas para las operaciones de base de datos.

Account.java: Define la entidad de cuenta bancaria.
Transaction.java: Define la entidad de transacción.
RiskRule.java: Define la entidad de reglas de riesgo para las transacciones.
repositories/: Repositorios para acceder a las bases de datos (MongoDB y JPA).

AccountRepository.java: Repositorio de cuentas bancarias.
TransactionRepository.java: Repositorio de transacciones.
RiskRuleRepository.java: Repositorio de reglas de riesgo.
services/: Servicios que contienen la lógica de negocio.

TransactionService.java: Maneja la creación de transacciones, validaciones y actualizaciones de saldo.
RiskService.java: Verifica si una transacción es permitida según las reglas de riesgo.
DataSeeder.java: Inicializa los datos de ejemplo para las reglas de riesgo y las cuentas bancarias.
exceptions/: Excepciones personalizadas.

BusinessException.java: Excepción personalizada para errores de negocio.
config/: Configuración de la aplicación y beans.

DataSeeder.java: Inicializa los datos cuando se arranca la aplicación.
utils/: Clases utilitarias y constantes.

Constants.java: Contiene las constantes utilizadas a lo largo del proyecto.
src/main/resources/
application.yml: Archivo de configuración principal para conectar con las bases de datos (MongoDB y H2).
static/: Archivos estáticos como imágenes, CSS, JS (si se usan para el frontend).
templates/: Plantillas de Thymeleaf (si se usan).
src/test/java/com/nttdata/transactions_service/
controllers/: Tests para los controladores REST.

TransactionControllerTest.java: Test para verificar los endpoints del controlador.
services/: Tests para los servicios.

RiskServiceTest.java: Test para verificar el comportamiento del servicio de riesgo.
TransactionServiceTest.java: Test para verificar el comportamiento del servicio de transacciones.
repositories/: Tests para los repositorios.

AccountRepositoryTest.java: Test para verificar el comportamiento del repositorio de cuentas.
Instrucciones de instalación
Herramientas necesarias
Java 17: Necesitas tener Java 17 o superior instalado en tu máquina. Si no lo tienes, puedes descargarlo desde aquí.

Maven: Asegúrate de tener Maven instalado. Puedes verificarlo con:

mvn -v
Si no tienes Maven instalado, puedes seguir esta guía para instalarlo.

Docker: Si deseas levantar MongoDB localmente usando Docker, asegúrate de tener Docker instalado. Puedes descargarlo desde aquí.

Postman (opcional): Para probar los endpoints de la API de manera más sencilla, puedes utilizar Postman.

Pasos de instalación

Clonar el repositorio: Clona el repositorio del proyecto en tu máquina local:

git clone https://github.com/tu-usuario/transactions-service.git cd transactions-service

Instalar dependencias: Asegúrate de tener Maven instalado y ejecuta el siguiente comando para descargar las dependencias del proyecto:

mvn clean install

Configurar MongoDB: Puedes levantar MongoDB de manera local utilizando Docker. Para hacerlo, asegúrate de tener Docker instalado en tu máquina y luego corre el siguiente comando en la raíz del proyecto donde se encuentra el archivo docker-compose.yml:

docker-compose up

Esto iniciará un contenedor de MongoDB en el puerto 27017.

Configuración de application.yml: Asegúrate de tener configurado correctamente el archivo application.yml en la carpeta src/main/resources/. Aquí tienes la configuración básica para conectar a MongoDB y H2 para pruebas locales:

server: port: 8084

spring: data: mongodb: uri: mongodb://localhost:27017/bankx # Conexión a MongoDB local jpa: hibernate: ddl-auto: create-drop # Para H2 show-sql: true h2: console: enabled: true path: /h2 logging: level: org.springframework.r2dbc: warn org.springframework.data.mongodb: info org.hibernate.SQL: debug

Explicación de la configuración:

mongodb.uri: Configura la URI para conectar a tu base de datos MongoDB local, en este caso a través de Docker.

jpa.hibernate.ddl-auto: Define el comportamiento de Hibernate con H2, en este caso create-drop para pruebas.

h2.console.enabled: Habilita la consola web para H2 en /h2 para acceder a la base de datos en memoria.

Levantar la aplicación: Una vez que tengas todo configurado, puedes levantar la aplicación usando Maven con el siguiente comando:

mvn spring-boot:run

La aplicación se ejecutará en el puerto 8084 y se conectará a la base de datos MongoDB para las operaciones reactivas y H2 para las pruebas locales.

Ejecutar las pruebas

Para ejecutar las pruebas unitarias de la aplicación, puedes usar Maven con el siguiente comando:

mvn test

Esto ejecutará todos los tests definidos en la carpeta src/test/java.

**Probar la API

Puedes probar la API utilizando Postman o cualquier herramienta que permita realizar solicitudes HTTP.**

POST /api/transactions: Crea una transacción.

Pasos de instalación

Clonar el repositorio: Clona el repositorio del proyecto en tu máquina local:

git clone https://github.com/tu-usuario/transactions-service.git cd transactions-service

Instalar dependencias: Asegúrate de tener Maven instalado y ejecuta el siguiente comando para descargar las dependencias del proyecto:

mvn clean install

Configurar MongoDB: Puedes levantar MongoDB de manera local utilizando Docker. Para hacerlo, asegúrate de tener Docker instalado en tu máquina y luego corre el siguiente comando en la raíz del proyecto donde se encuentra el archivo docker-compose.yml:

docker-compose up

Esto iniciará un contenedor de MongoDB en el puerto 27017.

Configuración de application.yml: Asegúrate de tener configurado correctamente el archivo application.yml en la carpeta src/main/resources/. Aquí tienes la configuración básica para conectar a MongoDB y H2 para pruebas locales:

server: port: 8084

spring: data: mongodb: uri: mongodb://localhost:27017/bankx # Conexión a MongoDB local jpa: hibernate: ddl-auto: create-drop # Para H2 show-sql: true h2: console: enabled: true path: /h2 logging: level: org.springframework.r2dbc: warn org.springframework.data.mongodb: info org.hibernate.SQL: debug

Explicación de la configuración:

mongodb.uri: Configura la URI para conectar a tu base de datos MongoDB local, en este caso a través de Docker.

jpa.hibernate.ddl-auto: Define el comportamiento de Hibernate con H2, en este caso create-drop para pruebas.

h2.console.enabled: Habilita la consola web para H2 en /h2 para acceder a la base de datos en memoria.

Levantar la aplicación: Una vez que tengas todo configurado, puedes levantar la aplicación usando Maven con el siguiente comando:

mvn spring-boot:run

La aplicación se ejecutará en el puerto 8084 y se conectará a la base de datos MongoDB para las operaciones reactivas y H2 para las pruebas locales.

Ejecutar las pruebas

Para ejecutar las pruebas unitarias de la aplicación, puedes usar Maven con el siguiente comando:

mvn test

Esto ejecutará todos los tests definidos en la carpeta src/test/java.

Probar la API

Puedes probar la API utilizando Postman o cualquier herramienta que permita realizar solicitudes HTTP.

POST /api/transactions: Crea una transacción.

Cuerpo de la solicitud (JSON):

{ "accountNumber": "001-0001", "type": "DEBIT", "amount": 1000 }

Respuesta esperada:

Código 201 si la transacción fue exitosa.

{ "accountNumber": "001-0001", "type": "DEBIT", "amount": 1000 }

Respuesta esperada:

Código 201 si la transacción fue exitosa.

POST /api/transactions: Crea una transacción (Rechazo por riesgo).

Cuerpo de la solicitud (JSON):

{ "accountNumber": "001-0001", "type": "DEBIT", "amount": 2000.00 }

Respuesta esperada: { "error": "Risk rejected" }

POST /api/transactions: Crea una transacción (Rechazo por fondos insuficientes).

Cuerpo de la solicitud (JSON):

{ "accountNumber": "001-0002", "type": "DEBIT", "amount": 2000 }

Respuesta esperada: { "error": "Insufficient funds" }

GET /api/transactions?accountNumber=?: Listar Transacciones por cuenta.

Params: accountNumber

Respuesta esperada:

Código 200 si la transacción fue exitosa.

GET /api/stream/transactions: Stream de Transacciones.

Respuesta esperada:

Código 200 si la transacción fue exitosa.
