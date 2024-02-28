package philAPIs;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mockAPIpojo.AdvancePrescription;
import mockAPIpojo.Appeal;
import mockAPIpojo.BaseData;
import mockAPIpojo.DrugDetails;
import mockAPIpojo.MockAPI;
import mockAPIpojo.PatientDetails;
import utilities.Configs;

public class mockOrder extends BaseTest {
	
	public static HashMap<String, String> createOrder(String program, String stage, String paymentType, String docterNPI) {
			
		MockAPI payload = new MockAPI();
		BaseData baseData = new BaseData();
		baseData.setProgramType(program);
		baseData.setStage(stage);
		baseData.setPaymentType(paymentType);
		baseData.setNpi(docterNPI);
		DrugDetails drugDetails = new DrugDetails();
		PatientDetails pd = new PatientDetails();
		baseData.setDrugDetails(drugDetails);
		baseData.setPatientDetails(pd);
		AdvancePrescription adv = new AdvancePrescription();
		adv.setStage(stage);
		Appeal app = new Appeal();
		adv.setAppeal(app);
		
		payload.setBaseData(baseData);
		payload.setAdvancePrescription(adv);
		
		RestAssured.baseURI = Configs.getProperty("mockOrder.baseURI").replace("env", env);
		
		Response response = given()
							   .contentType(ContentType.JSON)
							   .headers("Authorization", Configs.getProperty("philKey"))
							   .body(payload)
							   .when()
							   .post(Configs.getProperty("mockOrder.endPoint"));
		
		String rxNumber = response
						       .then()
						       .contentType(ContentType.JSON)
						       .extract()
						       .path("data.prescriptions[0].number");
		
		String patientName = response
							       .then()
							       .contentType(ContentType.JSON)
							       .extract()
							       .path("data.prescriptions[0].manager.fullName");
		
		String patientEmail = response
							       .then()
							       .contentType(ContentType.JSON)
							       .extract()
							       .path("data.prescriptions[0].manager.email");
		
		HashMap<String, String> responseInfo = new HashMap<String, String>();
		
		responseInfo.put("rxNumber", rxNumber);
		responseInfo.put("patientName", patientName);
		responseInfo.put("patientEmail", patientEmail);
		
		return responseInfo;
	}
}
