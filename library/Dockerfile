#RUN ./create-package.sh
FROM openjdk:11
VOLUME /tmp
ADD target/library-0.0.1-SNAPSHOT.jar library-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","library-0.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=prod"]