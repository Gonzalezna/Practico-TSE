FROM quay.io/wildfly/wildfly:37.0.0.Final-jdk21

COPY target/*.ear /opt/jboss/wildfly/standalone/deployments/
COPY configure.cli /tmp/configure.cli

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/tmp/configure.cli --echo-command

USER root
RUN chown -R jboss:root /opt/jboss/wildfly/standalone && chmod -R g+w /opt/jboss/wildfly/standalone
USER jboss

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone-full.xml"]