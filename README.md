# CineGTICS - Gestión de Películas
Aplicación web Spring Boot + Thymeleaf + MySQL contenerizada con Docker.

---

## Requisitos previos
- Docker Desktop instalado y corriendo
- Java 21 + Maven (para compilar el JAR)

---

## 1. Clonar y compilar

```bash
git clone https://github.com/TU_USUARIO/GTICS_LAB7_TUCODIGO.git
cd GTICS_LAB7_TUCODIGO
mvnw.cmd package -DskipTests
```

---

## 2. Despliegue LOCAL con Docker

### Paso 1 — Correr MySQL en Docker
```bash
docker run -d ^
  --name mysql-cine ^
  -e MYSQL_ROOT_PASSWORD=12345678 ^
  -e MYSQL_DATABASE=practicalab6 ^
  -p 3306:3306 ^
  -v %cd%\peliculas.sql:/docker-entrypoint-initdb.d/init.sql ^
  mysql:8.0
```
> Espera ~15 segundos para que MySQL inicialice antes de continuar.

### Paso 2 — Construir la imagen de la aplicación
```bash
docker build -t practicalab6:v1 .
```

### Paso 3 — Correr la aplicación en Docker (conectada al MySQL Docker)
```bash
docker run -d ^
  --name app-cine ^
  -p 8080:8080 ^
  --add-host=host.docker.internal:host-gateway ^
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/practicalab6?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true" ^
  -e SPRING_DATASOURCE_USERNAME=root ^
  -e SPRING_DATASOURCE_PASSWORD=12345678 ^
  practicalab6:v1
```

### Paso 4 — Verificar contenedores
```bash
docker ps
```

### Paso 5 — Acceder a la aplicación
- URL: http://localhost:8080
- Admin: admin@cine.com / admin123
- User:  user@cine.com  / user123

---

## 3. Despliegue REMOTO en AWS (EC2 + Docker)

### En la instancia EC2 — Instalar Docker (Amazon Linux 2023)
```bash
sudo yum install -y docker
sudo service docker start
sudo systemctl enable docker
sudo usermod -a -G docker ec2-user
# Cerrar y reabrir el terminal, luego verificar:
docker info
```

### En la instancia EC2 — Correr MySQL en Docker
```bash
docker run -d \
  --name mysql-cine \
  -e MYSQL_ROOT_PASSWORD=12345678 \
  -e MYSQL_DATABASE=practicalab6 \
  -p 3306:3306 \
  mysql:8.0
```
> Abrir puerto 3306 en el Security Group de AWS para tu IP local.

### Desde IntelliJ — Desplegar la app al Docker remoto
1. Run → Edit Configurations → + → Docker → Dockerfile
2. Server: crear nuevo Docker daemon remoto (SSH con tu .pem)
3. Variables de entorno:
```
SPRING_DATASOURCE_URL=jdbc:mysql://IP_PUBLICA_EC2:3306/practicalab6?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=12345678
```
4. Run options: `-p 8080:8080`
5. Abrir puerto 8080 en el Security Group de AWS.
6. Acceder: http://IP_PUBLICA_EC2:8080

---

## Estructura del proyecto
```
PracticaLab6/
├── Dockerfile
├── peliculas.sql
├── README.md
└── src/
    └── main/
        ├── java/com/example/practicalab6/
        │   ├── Controller/
        │   ├── Entity/
        │   ├── Repository/
        │   ├── Security/
        │   ├── Service/
        │   └── DataLoader.java
        └── resources/
            ├── application.properties
            └── templates/
```
