FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/tmp/jar/MANIFEST.MF MANIFEST.MF
COPY build/libs/unifatecieDev-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
