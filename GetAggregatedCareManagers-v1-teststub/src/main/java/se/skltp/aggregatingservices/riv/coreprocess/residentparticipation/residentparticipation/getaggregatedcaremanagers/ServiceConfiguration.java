package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagers.v1.rivtabp21.GetCareManagersResponderInterface;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagers.v1.rivtabp21.GetCareManagersResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedcaremanagers.v1.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  @SuppressWarnings("java:S1075") // URL hardcoded according to process
  public static final String SCHEMA_PATH = "/schemas/coreprocess-residentparticipation-residentparticipation/interactions/GetCareManagersInteraction/GetCareManagersInteraction_1.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetCareManagersResponderInterface.class.getName());
    setServiceNamespace("urn:riv:coreprocess:residentparticipation:residentparticipation:GetCareManagersResponder:1");
    setPortName(GetCareManagersResponderService.GetCareManagersResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
    setServiceTimeout(27000);
  }

}
