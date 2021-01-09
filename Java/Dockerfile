FROM maven:3.5.2-jdk-8-alpine AS maven_tool_chain
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package -Pprod

FROM openjdk:8-jdk-alpine
COPY --from=maven_tool_chain /tmp/target/targeter-server*.jar /jars/targeter/TargeterServer.jar
WORKDIR /jars/targeter
EXPOSE 8080
ENTRYPOINT ["java","-jar","TargeterServer.jar", "-Dspring.profiles.active=prod"]
