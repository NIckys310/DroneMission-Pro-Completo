# DroneMission Pro — proyecto completo

Sistema de gestión de misiones con drones: **Spring Boot 3 (Java 17) + MongoDB** en el backend y **React + TypeScript + Vite** en el frontend.
Incluye el código comentado, los diagramas de arquitectura 4+1 en Visual Paradigm y el material de exposición y estudio.

## Estructura

| Carpeta | Contenido |
|---|---|
| [`1-Codigo/`](1-Codigo) | `backend/` (API REST Spring Boot + MongoDB), `frontend/` (React), Dockerfile, `render.yaml`, scripts `.bat` para iniciar |
| [`2-Exposicion/`](2-Exposicion) | Presentación, **diagramas en Visual Paradigm** (`.vpp` + PNG), presentación GRASP, historias de usuario con mockups, colección de Postman |
| [`3-Estudio/`](3-Estudio) | Cómo leer los diagramas, dónde encontrar/crear/quitar código, Postman y MongoDB, quiz con respuestas, 10 ejemplos paso a paso, simulador de VS Code |

## Diagramas (vistas 4+1)

### 1 - Vista de contexto
![1 - Vista de contexto](2-Exposicion/2-Diagramas-Visual-Paradigm/png/1%20-%20Vista%20de%20contexto.png)

### 2 - Vista funcional (componentes)
![2 - Vista funcional (componentes)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/2%20-%20Vista%20funcional%20%28componentes%29.png)

### 3a - Vista lógica (dominio)
![3a - Vista lógica (dominio)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/3a%20-%20Vista%20l%C3%B3gica%20%28dominio%29.png)

### 3b - Vista lógica (patrones de diseño)
![3b - Vista lógica (patrones de diseño)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/3b%20-%20Vista%20l%C3%B3gica%20%28patrones%20de%20dise%C3%B1o%29.png)

### 4 - Vista de desarrollo (clases por capas)
![4 - Vista de desarrollo (clases por capas)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/4%20-%20Vista%20de%20desarrollo%20%28clases%20por%20capas%29.png)

### 5a - Vista de despliegue (producción en Render)
![5a - Vista de despliegue (producción en Render)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/5a%20-%20Vista%20de%20despliegue%20%28producci%C3%B3n%20en%20Render%29.png)

### 5b - Vista de despliegue (desarrollo local)
![5b - Vista de despliegue (desarrollo local)](2-Exposicion/2-Diagramas-Visual-Paradigm/png/5b%20-%20Vista%20de%20despliegue%20%28desarrollo%20local%29.png)

El proyecto editable es [`DroneMission-Pro-Vistas.vpp`](2-Exposicion/2-Diagramas-Visual-Paradigm/DroneMission-Pro-Vistas.vpp) (Visual Paradigm CE).

## Cómo ejecutarlo

1. **MongoDB**: instala MongoDB Community (o usa Atlas) y déjalo en `mongodb://localhost:27017`. *(La carpeta `1-Codigo/mongodb-local/bin` no se sube por su tamaño: copia ahí `mongod.exe` si quieres usar `iniciar-mongodb.bat`.)*
2. **Backend**: `cd 1-Codigo/backend` → `mvnw spring-boot:run -Dspring-boot.run.profiles=mongo` (puerto 8080). Sin MongoDB, el perfil `embedded` arranca una base en memoria.
3. **Frontend**: `cd 1-Codigo/frontend` → `npm install` → `npm run dev` (puerto 5173).

## Patrones de diseño en el código

| Patrón | Dónde |
|---|---|
| Builder | `missions/MissionBuilder.java` |
| Factory | `drones/DroneFactory.java` |
| Strategy | `missions/strategy/*Strategy.java` |
| State | `missions/MissionStatus.java` (`canTransitionTo`) |
| Observer | `missions/event/MissionEventPublisher.java` + listeners de auditoría y notificaciones |
