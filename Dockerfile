FROM jboss/wildfly:latest

ADD docker/customization /opt/jboss/wildfly/customization/
USER root
RUN chmod +x /opt/jboss/wildfly/customization/execute.sh

ADD docker/modules /opt/jboss/wildfly/modules/
RUN /opt/jboss/wildfly/customization/execute.sh
RUN rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current

ADD target/fishregistration-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
