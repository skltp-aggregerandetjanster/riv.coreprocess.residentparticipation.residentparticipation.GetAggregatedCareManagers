package se.skltp.aggregatingservices.riv.coreprocess.residentparticipation.residentparticipation.getaggregatedcaremanagers;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagersresponder.v1.GetCareManagersResponseType;
import riv.coreprocess.residentparticipation.residentparticipation.getcaremanagersresponder.v1.GetCareManagersType;
import riv.coreprocess.residentparticipation.residentparticipation.v1.*;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

	public static final String OID_HSA_ID = "1.2.752.129.2.1.4.1";
	public static final String OID_KV_TYP_AV_FAST_KONTAKT = "1.2.752.129.5.1.69";
	public static final String OID_FHIR_ADDRESS_TYPE = "2.16.840.1.113883.4.642.3.69";
	public static final String OID_HSA_BEFATTNING = "1.2.752.129.2.2.1.4";
	public static final String OID_SAMORDNINGSNUMMER = "1.2.752.129.2.1.3.3";
	public static final String OID_PERSONNUMMER = "1.2.752.129.2.1.3.1";

	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetCareManagersType request = (GetCareManagersType) messageContentsList.get(1);
		return request.getPatientId().toString();
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetCareManagersResponseType response = new GetCareManagersResponseType();
        for (Object responseItem : responseItems) {
            response.getCareManager().add((PractitionerRoleType) responseItem);
        }

		log.info("response.toString:{}", response);

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

		PractitionerRoleType practitionerRole = new PractitionerRoleType();
		practitionerRole.setCode(getCodeValue(OID_KV_TYP_AV_FAST_KONTAKT, "1")); // fast vårdkontakt
		practitionerRole.setPeriod(getDatePeriod());
		practitionerRole.setPractitioner(getPractitioner());
		practitionerRole.setInternalNotes("Intern kommentar");
		practitionerRole.setExternalNotes("Extern kommentar");
		practitionerRole.setManagingCareGiver(getOrganization("Z88"));
		practitionerRole.setManagingCareUnit(getOrganization("Z77"));
		practitionerRole.setCareProvidingCareUnit(getOrganization("Z66"));
		practitionerRole.getContact().add(getContact("postal", "AAA"));
		practitionerRole.getContact().add(getContact("physical", "BBB"));
		practitionerRole.setCareTeam(getCareTeam());

		return practitionerRole;
	}

	public Object createRequest(String patientId, String sourceSystemHSAId){
		GetCareManagersType requestType = new GetCareManagersType();

		IIType value;
		if (patientId != null && patientId.length() == 12 && patientId.charAt(4) >= '6') { // Samordningsnummer
			value = getUniqueId(OID_SAMORDNINGSNUMMER, patientId);
		} else { // Assume it's a regular personnummer
			value = getUniqueId(OID_PERSONNUMMER, patientId);
		}
		requestType.setPatientId(value);

		return requestType;
	}

	private @NonNull DatePeriodType getDatePeriod() {
		DatePeriodType datePeriodType = new DatePeriodType();
		datePeriodType.setStart("2024-01-01");
		datePeriodType.setEnd("2024-12-31");
		return datePeriodType;
	}

	private @NonNull CareTeamType getCareTeam() {
		CareTeamType careTeam = new CareTeamType();

		careTeam.setId(getUniqueId(OID_HSA_ID, "TSTDEF2321000156-ABC"));
		careTeam.setName("Vårdteam ABC");
		careTeam.setInternalNotes("Intern kommentar för vårdteam ABC");
		careTeam.setExternalNotes("Extern kommentar för vårdteam ABC");
		careTeam.getContact().add(getContact("both", "ABC"));

		return careTeam;
	}

	private @NonNull ContactType getContact(String addressType, String citySuffix) {
		CVType cvType = getCodeValue(OID_FHIR_ADDRESS_TYPE, addressType); // Postadress

		AddressType address = new AddressType();
		address.setType(cvType);
		address.setCity("Stad " + citySuffix);
		address.setPostalCode("12345");

		ContactType contact = new ContactType();
		contact.setAddress(address);
		return contact;
	}

	private @NonNull OrganizationType getOrganization(String suffix) {
		IIType hsaId = getUniqueId(OID_HSA_ID, "TSTDEF2321000156-" + suffix);

		OrganizationType organization = new OrganizationType();
		organization.setHsaId(hsaId);
		organization.setName("Vårdgivare " + suffix);
		return organization;
	}

	private @NonNull PractitionerType getPractitioner() {
		PractitionerType practitionerType = new PractitionerType();
		practitionerType.setHsaId(getUniqueId(OID_HSA_ID, "TSTABC2321000156-Z99"));
		practitionerType.setName("Test Testsson");
		practitionerType.setQualification(getCodeValue(OID_HSA_BEFATTNING, "201011")); // Distriktsläkare/Specialist allmänmedicin
		return practitionerType;
	}

	private static @NonNull IIType getUniqueId(String root, String extension) {
		IIType uniqueId = new IIType();
		uniqueId.setRoot(root);
		uniqueId.setExtension(extension);
		return uniqueId;
	}

	private @NonNull CVType getCodeValue(String codeSystem, String code) {
		CVType cvType = new CVType();
		cvType.setCodeSystem(codeSystem);
		cvType.setCode(code);
		return cvType;
	}
}
