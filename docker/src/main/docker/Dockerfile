FROM daggerok/jboss-eap-6.4:6.4.0-alpine
RUN echo "JAVA_OPTS=\"\$JAVA_OPTS -Djboss.bind.address=0.0.0.0 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005\"" >> ${JBOSS_HOME}/bin/standalone.conf
EXPOSE 5005 9990 9999
HEALTHCHECK --timeout=1s --retries=66 \
        CMD wget -q --spider http://127.0.0.1:8080/blog/api/health \
         || exit 1
ADD --chown=jboss ./docker/target/modules/org/postgresql/main/*.jar                               ${JBOSS_HOME}/modules/org/postgresql/main/postgresql-9.4-1206-jdbc4.jar
ADD --chown=jboss ./docker/src/main/docker/jboss-eap-6.4/modules/org/postgresql/main/module.xml   ${JBOSS_HOME}/modules/org/postgresql/main/module.xml
ADD --chown=jboss ./docker/src/main/docker/jboss-eap-6.4/standalone/configuration/standalone.xml  ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD --chown=jboss ./backend/target/*.war                                                          ${JBOSS_HOME}/standalone/deployments/
