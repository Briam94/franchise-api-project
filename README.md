# API de Gestión de Franquicias

Una aplicación reactiva y funcional en Spring Boot 17 que permite crear y gestionar franquicias, sucursales y productos. 
Sigue la Clean Architecture del scaffold de Bancolombia y utiliza MongoDB reactivo para persistencia.

---

## Tabla de contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Prerrequisitos](#prerrequisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Infraestructura como código](#infraestructura-como-código)
- [Endpoints de la API](#endpoints-de-la-api)

---

## Características

- Crear y renombrar franquicias
- Agregar y renombrar sucursales
- Agregar, eliminar, renombrar y actualizar stock de productos
- Obtener el producto de mayor stock por cada sucursal
- Programación reactiva y no bloqueante con Spring WebFlux
- Enrutamiento funcional con RouterFunctions y handlers
- Arquitectura limpia modular siguiendo el scaffold de Bancolombia
- Persistencia reactiva en MongoDB
- Documentación automática con OpenAPI/Swagger
- Contenerización con Docker

---

## Tecnologías

- Java 17
- Spring Boot 3.1.0
- Spring WebFlux (reactivo y funcional)
- Spring Data Reactive MongoDB
- Project Reactor (Mono, Flux)
- Springdoc OpenAPI 3
- JUnit 5, Mockito, Reactor Test
- Docker
- Terraform (infraestructura)

---

## Prerrequisitos

- Java 17 (JDK) instalado
- Docker y Docker Compose (opcional para contenerización)
- Terraform >= 1.0 (para aprovisionar infraestructura)

---

## Instalación

Clona el repositorio y accede al directorio del proyecto:
https://github.com/Briam94/franchise-api-project.git

---
## Configuración
Define la conexión a MongoDB en src/main/resources/application.properties o mediante variables de entorno:

spring.data.mongodb.uri= mongodb://localhost:27017/franchisesdb
spring.web.flux.base-path= /api

ejecuta el archivo src/main/resources/seed.js para generar los primeros datos de prueba
Asegúrate de tener MongoDB corriendo y accesible.

en el MongoDbCompass importa el archivo src/main/resources/seed.js para generar los primeros datos de prueba.

---

## Infraestructura como código
En la carpeta terraform/ encontrarás ejemplos para provisionar un clúster de MongoDB (por ejemplo, en Atlas) 
usando Terraform. Para aplicar:

cd terraform
terraform init
terraform apply

---

## Endpoints de la API
Base URL: http://localhost:8080/api

Método	Ruta	Descripción	DTO de entrada
POST	/franchises	Crear nueva franquicia	FranchiseDto { name }
PUT	    /franchises/{franchiseId}/name	Renombrar franquicia	NameDto { name }
POST	/franchises/{franchiseId}/branches	Agregar sucursal a una franquicia	BranchDto { name }
PUT	    /franchises/{franchiseId}/branches/{branchId}/name	Renombrar sucursal	NameDto { name }
POST	/franchises/{franchiseId}/branches/{branchId}/products	Agregar producto a sucursal	ProductDto { name, stock }
DELETE	/franchises/{franchiseId}/branches/{branchId}/products/{productId}	Eliminar producto de sucursal	—
PATCH	/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock	Actualizar stock de producto	StockDto { stock }
PUT	    /franchises/{franchiseId}/branches/{branchId}/products/{productId}/name	Renombrar producto	NameDto { name }
GET	    /franchises/{franchiseId}/max-stock	Obtener producto de mayor stock por sucursal	—


