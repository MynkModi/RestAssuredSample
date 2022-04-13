package APIDemoPkg;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

public class RestAssuredDemo {
	
	
	@Test
	//public static void main(String[] args) 
	public void getData()
	{
	
		
		RestAssured.baseURI="https://maps.googleapis.com";
		
		given().
				param("location","-33.8670522,151.1957362").
				param("key","AIzaSyB38za98Y_eMkSsF4CLWXzysHj0t8urmCA").log().all().
				param("radius","1500").
				when().get("/maps/api/place/nearbysearch/json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
				and().body("results[0].geometry.location.lat",equalTo("-33.8670522")).
				and().body("results[0].name",equalTo("Sydney")).
				and().body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).
				and().header("Server", "scaffolding on HTTPServer2").log().body();
				
				
				/*header("abc","abc").
				cookie("abc","abc").
				body("abc","abc")*/
						
	}

}
