FROM maven:3.9-eclipse-temurin-17 AS build
ARG SERVICE
ARG JAR_DIR
WORKDIR /build
COPY ${SERVICE}/ .
RUN mvn -q -B -DskipTests package
RUN cp ${JAR_DIR}/*.jar /build/app.jar

FROM ghcr.io/graalvm/jdk-community:17
WORKDIR /app
COPY --from=build /build/app.jar app.jar
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-jar", "app.jar"]
