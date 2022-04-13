//https://techndeck.com/rest-assured-tutorial-rest-api-testing/
//https://techndeck.com/how-to-make-a-get-request-using-apache-httpclient-in-java/

package APIDemoPkg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import io.restassured.path.json.JsonPath;

public class APIDemoTechnDeck 
{
	
	@Test(priority=1)
	public void postEmpData()
	{
		String respBody="{"+
				 "\"name\":\"eileen\","+
				 "\"salary\":\"5000\","+
				 "\"age\":\"20\""+
				"}";
				
		RestAssured.baseURI="http://dummy.restapiexample.com";
		
		Response resp=given().contentType(ContentType.JSON).body(respBody).
		when().post("/api/v1/create").
		then().assertThat().statusCode(200).
		and().body("status", equalTo("success")).
		extract().response();
		
		System.out.println("Response :" + resp.asString());
	    System.out.println("Does Reponse contains 'eileen'? :" + resp.asString().contains("eileen"));
	     
	    JsonPath jsEvaluator=resp.jsonPath();
		System.out.println("newly created id is: "+jsEvaluator.get("data.id"));
		
		String responseBody = resp.getBody().prettyPrint();//.asString();
		try {
            FileWriter file = new FileWriter("src//test//java//files//actualresponse.json");
            file.write(responseBody);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
			     
	}
	
	// (enabled = false)
	@Test (priority=2)
	public void getEmployeeData()
	{
		RestAssured.baseURI="http://dummy.restapiexample.com";
	
		
		Response actualResp=given().
		when().get("/api/v1/employees").
		then().assertThat().statusCode(200).
		extract().response(); 
		
		
		System.out.println("Status code is: "+actualResp.getStatusCode());
			
		String strResp=actualResp.asString();
		System.out.println("Full response body: "+strResp);	
		System.out.println("Does Reponse contains 'employee_salary'? :" + strResp.contains("employee_salary"));
		
		JsonPath jsEvaluator=actualResp.jsonPath();
		System.out.println("json value of Status: "+jsEvaluator.get("status"));
		System.out.println("json value of Status: "+jsEvaluator.getString("status"));
		
		
		//parsing json arrays
		//get list of all emp names
		System.out.println("Emp : \n"+jsEvaluator.get("data.employee_name"));
		
	/*	//another way to write above
		List <String> allEmpName=jsEvaluator.getList("data.employee_name");
		for(int i=0;i<allEmpName.size();i++){
			System.out.println("Emp Nams: " +allEmpName.get(i) );
		}*/
		
		//get name of 2nd emp
		System.out.println("Emp#2 : \n"+jsEvaluator.get("data.employee_name[1]"));
	}
	
	
	@Test(priority=3)
	public void delEmpData()
	{
		RestAssured.baseURI="http://dummy.restapiexample.com";
		
		//deleting : /api/v1/delete/nth id
		Response resp=given().
		when().delete("/api/v1/delete/99").
		then().assertThat().statusCode(200).and().body("status", equalTo("passed")).
		extract().response();
		
		JsonPath jsValidator=resp.jsonPath();
		System.out.println("Actual message is: "+jsValidator.get("message"));
	}
	

}//end of class
