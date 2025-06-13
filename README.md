# Audit Management Studio backend

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Autenticación](#autenticación)
- [Documentación de la API](#documentación-de-la-api)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)

---

## Descripción General

### Funcionalidades principales:

- ✨ Gestión de auditorías: Sistema completo para la gestión y seguimiento de auditorías.
- ✨ Autenticación segura: Implementación de JWT para la seguridad de la aplicación.
- ✨ Transformación de archivos Excel a JSON: Integración con Cloudmersive API para convertir archivos Excel en formato JSON.

### Arquitectura:

- Backend: **Java 17**, **Spring Boot**.
- Base de datos: **Microsoft SQL Server**.
- API Externa: Integración con **Cloudmersive API** para procesamiento de archivos Excel.

---

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot** (versión: `3.0.5`)
- Librerías:
  - [Spring Security](https://spring.io/projects/spring-security): Para autenticación y autorización.
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Para acceso a la base de datos.
  - [Springdoc OpenAPI](https://springdoc.org/): Para generar y visualizar documentación de la API.
  - [JWT](https://jwt.io/): Para manejo de tokens.
  - [Cloudmersive API](https://cloudmersive.com/): Para la transformación de archivos Excel a JSON.

---

## Requisitos Previos

Asegúrate de tener instalados:

1. **Java 17+**: [Descargar e instalar](https://www.oracle.com/java/technologies/javase-downloads.html)
2. **Maven 3.x**: [Descargar Maven](https://maven.apache.org/download.cgi)
3. **Base de datos**: [Descargar Microsoft SQL](https://www.microsoft.com/es-ar/sql-server/sql-server-downloads)
4. **Git**: [Descargue Git](https://git-scm.com/)
5. **Clave API de Cloudmersive**: Necesaria para la transformación de archivos Excel

## Ejecución de la Aplicación

### Modo Desarrollo Local

Hay dos formas de ejecutar la aplicación en modo local:

1. Usando un solo comando (recomendado):

```powershell
$env:SPRING_PROFILES_ACTIVE="local"; $env:JWT_SECRET="RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512"; mvn spring-boot:run
```

2. Configurando las variables de entorno por separado:

```powershell
$env:SPRING_PROFILES_ACTIVE="local"
$env:JWT_SECRET="RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512RenderAMSSecureKeyHS512"
mvn spring-boot:run
```

### Modo Producción

Para ejecutar la aplicación en modo producción:

1. Construir la aplicación:

```powershell
npm run build
```

2. Servir la aplicación construida:

```powershell
serve -s build
```

---

## Configuración

### Variables de entorno necesarias

- `SPRING_PROFILES_ACTIVE`: Define el perfil de ejecución (local/prod)
- `CLOUDMERSIVE_API_KEY`: Clave de API para Cloudmersive
- Configuración de base de datos en `application-{profile}.properties`

---

## Documentación de la API

La documentación de la API está disponible en `/swagger-ui.html` cuando la aplicación está en ejecución.

---
