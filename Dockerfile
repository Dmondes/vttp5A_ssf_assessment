FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/compiledir
WORKDIR ${COMPILE_DIR}

#Copy build files
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY src src
COPY .mvn .mvn

#Build app
# RUN chmod a+x ./mvnw 
RUN ./mvnw clean package -Dmaven.skip.tests=true

ENTRYPOINT ["java", "-jar", "target/noticeboard-0.0.1-SNAPSHOT.jar"]

# second stage Run time
FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app
WORKDIR ${WORK_DIR}

#Copy jar from builder
COPY --from=builder /compiledir/target/*.jar buildtoo-final.jar

ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar buildtoo-final.jar

HEALTHCHECK --interval=60s --timeout=30s --start-period=120s  CMD curl -s -f http:/localhost:8080/demo/health || exit 1