package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagersresponder.v1.GetCareManagersResponseType;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagersresponder.v1.GetCareManagersType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.v1.EngagementType;

import static se.skltp.aggregatingservices.utility.RequestListUtil.createRequest;

@Log4j2
public class GACM1AgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetCareManagersType, GetCareManagersResponseType>{

  @Override
  public String getPatientId(GetCareManagersType queryObject){
    return queryObject.getPatientId().getExtension();
  }

  @Override
  public String getSourceSystemHsaId(GetCareManagersType queryObject){
    return null;
  }

  @Override
  public GetCareManagersResponseType aggregateResponse(List<GetCareManagersResponseType> aggregatedResponseList) {
    GetCareManagersResponseType aggregatedResponse = new GetCareManagersResponseType();

    for (GetCareManagersResponseType response : aggregatedResponseList) {
      aggregatedResponse.getCareManager().addAll(response.getCareManager());
    }

    return aggregatedResponse;
  }

  @Override
  public List<MessageContentsList> createRequestList(
          MessageContentsList messageContentsList, FindContentResponseType eiResp) {
    int initialCapacity = eiResp.getEngagement().size();
    log.info("Got {} hits in the engagement index", initialCapacity);
    Set<String> addresses = new HashSet<>(initialCapacity);
    for (EngagementType e : eiResp.getEngagement()) {
      String la = e.getLogicalAddress();
      if (la != null) {
        addresses.add(la);
      }
    }
    List<MessageContentsList> reqList = new ArrayList<>(addresses.size());
    for (String la : addresses) {
      log.info("Calling source system using logical address {}", la);
      reqList.add(createRequest(la, messageContentsList));
    }
    log.info("Calling {} source systems", reqList.size());
    return reqList;
  }
}

