
package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagers.v1.rivtabp21.GetCareManagersResponderInterface;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagers.v1.rivtabp21.GetCareManagersResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedcaremanagers.v1")
public class GACM1AgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

  @SuppressWarnings("java:S1075") // URL hardcoded according to process
  public static final String SCHEMA_PATH = "/schemas/coreprocess-residentparticipation-residentparticipation/interactions/GetCareManagersInteraction/GetCareManagersInteraction_1.0_RIVTABP21.wsdl";

  public GACM1AgpServiceConfiguration() {

    setServiceName("GetAggregatedCareManagers-v1");
    setTargetNamespace("urn:riv:coreprocess:residentparticipation:residentparticipation:GetCareManagers:1:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:8081/GetAggregatedCareManagers/service/v1");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetCareManagersResponderInterface.class.getName());
    setInboundPortName(GetCareManagersResponderService.GetCareManagersResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(getInboundServiceClass());
    setOutboundPortName(getInboundPortName());

    // FindContent
    setEiServiceDomain("riv:coreprocess:residentparticipation:residentparticipation");
    setEiCategorization("crr-gcm");

    // TAK
    setTakContract("urn:riv:coreprocess:residentparticipation:residentparticipation:GetCareManagersResponder:1");

    // Set service factory
    setServiceFactoryClass(GACM1AgpServiceFactoryImpl.class.getName());

    // Validate incoming messages against the schema, reject messages that do not conform
    setEnableSchemaValidation(true);
  }
}
