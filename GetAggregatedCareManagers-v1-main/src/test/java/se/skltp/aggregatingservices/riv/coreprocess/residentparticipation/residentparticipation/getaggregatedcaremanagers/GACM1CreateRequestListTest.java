package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import org.apache.cxf.message.MessageContentsList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.tests.CreateRequestListTest;
import se.skltp.aggregatingservices.tests.TestDataUtil;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"}) // Base class uses raw types
@ExtendWith(SpringExtension.class)
public class GACM1CreateRequestListTest extends CreateRequestListTest {

  public GACM1CreateRequestListTest() {
    super(new ServiceTestDataGenerator(), new GACM1AgpServiceFactoryImpl(), new GACM1AgpServiceConfiguration());
  }

  @Test
  @Override // Filtering by source system isn't supported
  public void testCreateRequestListSomeFilteredBySourceSystem() {
    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", this.testDataGenerator.createRequest("198611062384", "HSA-ID-2"));
    FindContentResponseType eiResponse = this.eiResponseDataHelper.getResponseForPatient("198611062384");
    for (var engagement : eiResponse.getEngagement()) { // Make sure source system isn't used instead of logical address
      engagement.setSourceSystem("DONT-USE-SOURCE-SYSTEM");
    }
    List<MessageContentsList> requestList = (List<MessageContentsList>) this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    Assertions.assertEquals(3, requestList.size());
    Set<Object> expectedHsaIds = Set.of("HSA-ID-1", "HSA-ID-2", "HSA-ID-3");
    Set<Object> actualHsaIds = requestList.stream()
            .map(req -> req.get(0))
            .collect(java.util.stream.Collectors.toSet());

    Assertions.assertEquals(expectedHsaIds, actualHsaIds);
  }

  @Test
  @Override // Filtering by source system isn't supported
  public void testCreateRequestListAllFilteredBySourceSystem() {
    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", this.testDataGenerator.createRequest("121212121212", "HSA-ID-2"));
    FindContentResponseType eiResponse = this.eiResponseDataHelper.getResponseForPatient("121212121212");
    List requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    Assertions.assertEquals(3, requestList.size());
  }
}