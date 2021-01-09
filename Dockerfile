FROM maven:3.5.2-jdk-8-alpine AS maven_tool_chain
COPY Java/pom.xml /project/Java/
COPY /client /project/client/
COPY Java/src /project/Java/src/
WORKDIR /project/Java/
RUN mvn package -DskipTests -Pprod

FROM openjdk:8-jdk-alpine
COPY --from=maven_tool_chain /project/Java/target/targeter-server*.jar /jars/targeter/TargeterServer.jar
WORKDIR /jars/targeter
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","TargeterServer.jar"]
