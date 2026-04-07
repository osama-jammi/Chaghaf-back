FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S chaghaf && adduser -S chaghaf -G chaghaf
COPY target/*.jar app.jar
RUN chown chaghaf:chaghaf app.jar
USER chaghaf
EXPOSE 8086
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8086/actuator/health || exit 1
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
