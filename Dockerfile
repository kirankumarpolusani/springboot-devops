FROM java:8-jdk-alpine

COPY ./target/feecalc-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch feecalc-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","feecalc-0.0.1-SNAPSHOT.jar"]
