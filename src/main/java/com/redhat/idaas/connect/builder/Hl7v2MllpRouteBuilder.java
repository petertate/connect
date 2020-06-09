package com.redhat.idaas.connect.builder;

import com.redhat.idaas.connect.configuration.EndpointUriBuilder;
import org.apache.camel.LoggingLevel;
import org.apache.camel.component.kafka.KafkaConstants;

/**
 * Defines a HL7 V2 MLLP processing route
 */
public class Hl7v2MllpRouteBuilder extends IdaasRouteBuilder {

    @Override
    public void configure() {
        EndpointUriBuilder uriBuilder = getEndpointUriBuilder();
        String consumerUri = uriBuilder.getHl7V2MllpUri();
        String producerUri = uriBuilder.getDataStoreUri("HL7v2_${headers[CamelHL7MessageType]}");

        from(consumerUri)
                .routeId("hl7-v2-mllp")
                .unmarshal().hl7()
                .log(LoggingLevel.INFO, "${body}")
                .setHeader(KafkaConstants.KEY, constant("Camel"))
                .toD(producerUri);
    }
}
