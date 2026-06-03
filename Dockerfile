# ============================================================
#  Dockerfile - PracticaLab6 (Spring Boot + Java 21)
#
#  PASO 1: Generar el JAR:
#    mvnw.cmd package -DskipTests
#
#  PASO 2: Construir la imagen:
#    docker build -t practicalab6:v1 .
#
#  PASO 3: Correr el contenedor conectado a MySQL local:
#    docker run -d -p 8080:8080 ^
#      --add-host=host.docker.internal:host-gateway ^
#      -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/practicalab6?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true" ^
#      -e SPRING_DATASOURCE_USERNAME=root ^
#      -e SPRING_DATASOURCE_PASSWORD=12345678 ^
#      practicalab6:v1
#
#  PASO 4: Verificar en http://localhost:8080
# ============================================================

# eclipse-temurin es el reemplazo oficial de openjdk (que fue deprecado)
FROM eclipse-temurin:21-jdk-jammy

# Directorio temporal para Spring Boot
VOLUME /tmp

# Puerto de la aplicación
EXPOSE 8080

# Copiar el JAR al contenedor
ADD target/PracticaLab6-0.0.1-SNAPSHOT.jar app.jar

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
