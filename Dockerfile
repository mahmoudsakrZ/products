FROM openjdk:11-jre

LABEL Name="Products"
LABEL Description="Vodafone IOT Devices Shop"
LABEL Maintainer="mahmoudragab726@gmail.com"
LABEL Url=""
COPY ./target/products*.jar products.jar

EXPOSE 9090

HEALTHCHECK --start-period=60s \
  CMD curl -f http://localhost:9090/actuator/health || exit 1

USER 1001

ENTRYPOINT ["java","-jar","/products.jar"]
