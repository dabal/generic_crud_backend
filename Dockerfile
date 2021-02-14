FROM maven:3-openjdk-11 AS builder

RUN mkdir multithreaded-echo-server
WORKDIR faustyna-translations-backend
COPY . .
RUN mvn -s ./.m2/settings.xml clean package -Dmaven.test.skip=true


FROM dabal/jre11-alpine:latest
EXPOSE 8080
COPY --from=builder /faustyna-translations-backend/target/gallery.translation-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","gallery.translation-0.0.1-SNAPSHOT.jar"]