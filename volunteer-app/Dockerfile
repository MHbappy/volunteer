#RUN ./create-package.sh
FROM openjdk:11
VOLUME /tmp
ADD target/volunteer-0.0.1-SNAPSHOT.jar volunteer-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","volunteer-0.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=prod"]