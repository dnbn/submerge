FROM amazoncorretto:17
RUN yum update -y && yum -y install shadow-utils.aarch64
RUN groupadd -g 1000 spring && useradd -u 1000 -g 1000 spring
USER spring:spring
COPY target/submerge-boot-2.0.2.war /home/spring/app.war
ENTRYPOINT ["java" , "--add-opens=java.base/java.lang=ALL-UNNAMED", "-jar","/home/spring/app.war"]