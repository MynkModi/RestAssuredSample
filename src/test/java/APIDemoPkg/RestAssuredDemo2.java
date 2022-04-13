package APIDemoPkg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

public class RestAssuredDemo2 

{
		@Test
		public void postData()
		{
			
			String postResp="{"+
	"\"location\":{"+
	"\"lat\" : -38.383494,"+
        "\"lng\" : 33.427362"+
    "},"+
    "\"accuracy\":50,"+
    "\"name\":\"Frontline house\","+
    "\"phone_number\":\"(+91) 983 893 3937\","+
    "\"address\" : \"29, side layout, cohen 09\","+
    "\"types\": [\"shoe park\",\"shop\"],"+
    "\"website\" : \"http://google.com\","+
    "\"language\" : \"French-IN\""+
"}";
			
			RestAssured.baseURI= "http://216.10.245.166";
			
			given().
			queryParam("key","qaclick123").
			body(postResp).
			when().post("/maps/api/place/add/json").
			then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
			and().body("status", equalTo("OK"));
		}
		
		//create a place and delete that
		
		
		/*given().
		queryParam("key","qaclick123").
		body("{"place_id":"928b51f64aed18713b0d164d9be8d67f"}")*/
}
