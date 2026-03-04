package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;


@ExtendWith(SpringExtension.class)
public class GACM1CreateFindContentTest extends CreateFindContentTest {

  public GACM1CreateFindContentTest() {
    super(new ServiceTestDataGenerator(), new GACM1AgpServiceFactoryImpl(), new GACM1AgpServiceConfiguration());
  }

}
