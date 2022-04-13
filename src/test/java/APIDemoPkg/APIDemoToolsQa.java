
//https://www.toolsqa.com/rest-assured-tutorial/

package APIDemoPkg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.path.json.JsonPath;

public class APIDemoToolsQa 
{
	@Test
	public void getCityData()
	{
		RestAssured.baseURI="http://restapi.demoqa.com";
		
		given().
		when().get("/utilities/weather/city/Hyderabad").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		and().body("City", equalTo("Hyderabad")).
		and().body("Temperature", equalTo("36 Degree celsius"	)).
		and().body("Humidity", equalTo("28 Percent"	)).
		and().body( "WeatherDescription", equalTo("scattered clouds")).
		and().body("WindSpeed", equalTo("1.5 Km per hour")).
		and().body("WindDirectionDegree", equalTo("330 Degree")).
		and().header("Server", equalTo("nginx"));
		
		Response resp= given().
		when().get("/utilities/weather/city/Hyderabad").
		then().assertThat().statusCode(200).
		extract().response();
				
		//String strResponse=resp.getBody().asString();
		String strResponse=resp.asString();
		System.out.println("Response Body is: "+strResponse);
		
		//JsonPath demop.
		JsonPath jsEvaluator=resp.jsonPath();
		System.out.println("Response City is: " +jsEvaluator.get("City"));
		
	}
	
	
/*	@Test
	public void postCityData()
	{
		RestAssured.baseURI="http://restapi.demoqa.com";
		
		given().
		when().get("/utilities/weather/city/Hyderabad").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		and().body("City", equalTo("Hyderabad")).
		and().body("Temperature", equalTo("38.82 Degree celsius"	)).
		and().body("Humidity", equalTo("11 Percent"	)).
		and().body( "WeatherDescription", equalTo("scattered clouds")).
		and().body("WindSpeed", equalTo("6.58 Km per hour")).
		and().body("WindDirectionDegree", equalTo("340 Degrees"));
		
	}
*/
	
}
