FROM openjdk:18
EXPOSE 8080
COPY movies_metadata.csv movies_metadata.csv
VOLUME /tmp
COPY target/spring-boot-guild.jar spring-boot-guild.jar
ENTRYPOINT ["java","-jar","spring-boot-guild.jar"]