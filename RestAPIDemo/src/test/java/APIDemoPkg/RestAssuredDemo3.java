package APIDemoPkg;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import files.Resources;
import files.PayLoad;

public class RestAssuredDemo3 
{
	Properties prop=new Properties();
	@BeforeTest
	public void SuiteSetUp() throws IOException
	{
		FileInputStream fis=new FileInputStream("C:\\eclipse-workspace\\RestAPIDemo\\src\\test\\java\\files\\env.properties");
		prop.load(fis);	
	}
	
	@Test
	public void AddandDeleteApi()
 {

	/*String postResp="{"+
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
	"}";*/
		
	   //Task 1: grab the responnse
		//RestAssured.baseURI= "http://216.10.245.166";
		RestAssured.baseURI= prop.getProperty("HOST");
		
		Response resp=given().
		queryParam("key",prop.getProperty("KEY")).
		//body(postResp).
		body(PayLoad.getPostData()).
		when().post(Resources.placePostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		and().body("status", equalTo("OK")).
		extract().response();
		
		String responseString=resp.asString();
		System.out.println(responseString);
	
	//Task 2 : grab the placeid(value)
	
		
		JsonPath js=new JsonPath(responseString);
		String plcId=js.get("place_id");
		System.out.println(plcId);
		
		//Task 3 : use place id to delete the req	
		//create a place and delete that
	given().
	queryParam("key","qaclick123").
	body("{"+
"\"place_id\":\""+plcId+"\""+
"}").
	when().post("/maps/api/place/delete/json").
	then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
	and().body("status", equalTo("OK"));

  }
}
