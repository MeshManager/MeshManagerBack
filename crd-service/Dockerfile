FROM public.ecr.aws/docker/library/gradle:jdk21-corretto AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

RUN gradle dependencies --no-daemon

COPY src /app/src

ARG GRADLE_BUILD_ARGS="clean build"
RUN gradle ${GRADLE_BUILD_ARGS} --no-daemon


FROM public.ecr.aws/amazoncorretto/amazoncorretto:21-al2023-headless

WORKDIR /app

ADD https://dtdg.co/latest-java-tracer /app/dd-java-agent.jar

COPY --from=build /app/build/libs/application.jar /app/application.jar

EXPOSE 8080

CMD java -javaagent:/app/dd-java-agent.jar -jar /app/application.jar