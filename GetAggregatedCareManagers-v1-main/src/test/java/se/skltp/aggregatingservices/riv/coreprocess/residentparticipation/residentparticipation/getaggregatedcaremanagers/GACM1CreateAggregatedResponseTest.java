package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagersresponder.v1.GetCareManagersResponseType;


@ExtendWith(SpringExtension.class)
public class GACM1CreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  public GACM1CreateAggregatedResponseTest() {
      super(new ServiceTestDataGenerator(), new GACM1AgpServiceFactoryImpl(), new GACM1AgpServiceConfiguration());
  }

  @Override
  public int getResponseSize(Object response) {
        GetCareManagersResponseType responseType = (GetCareManagersResponseType)response;
    return responseType.getCareManager().size();
  }
}