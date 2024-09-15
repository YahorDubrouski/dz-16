import io.restassured.builder.RequestSpecBuilder;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BookerApiTest {

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .addHeader("Content-Type", "application/json")
                .build()
                .filter(new AllureRestAssured());
    }

    @Test
    public void postBookingTest() {
        JSONObject body = new JSONObject();
        body.put("firstname", "Jim");
        body.put("lastname", "Brown");
        body.put("totalprice", 111);
        body.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Breakfast");

        Response response = RestAssured
                .given()
                .body(body.toString())
                .post();
        response.then().log().all().statusCode(200);
    }

    @Test
    public void getAllBookingsTest() {
        Response response = RestAssured
                .given()
                .get();
        response.then().log().all().statusCode(200);
    }

    @Test
    public void patchUpdateBookingTest() {
        Response bookingsResponse = RestAssured
                .given()
                .get();
        bookingsResponse.then().log().all().statusCode(200);
        JsonPath jsonPath = bookingsResponse.jsonPath();
        Integer bookingId = jsonPath.getInt("[0].bookingid");

        JSONObject updateBody = new JSONObject();
        updateBody.put("totalprice", 10);
        Response updateResponse = RestAssured
                .given()
                .body(updateBody.toString())
                .patch("/{id}", bookingId);
        updateResponse.then().log().all().statusCode(200);
    }

    @Test
    public void putUpdateBookingTest() {
        Response bookingsResponse = RestAssured
                .given()
                .get();
        bookingsResponse.then().log().all().statusCode(200);
        JsonPath jsonPath = bookingsResponse.jsonPath();
        Integer bookingId = jsonPath.getInt("[1].bookingid");
        Response bookingResponse = RestAssured
                .given()
                .get("/{id}}", bookingId);
        bookingResponse.then().log().all().statusCode(200);

        JSONObject booking = new JSONObject(bookingResponse.getBody().asString());

        booking.put("firstname", "Bob");
        booking.put("additionalneeds", "Lunch");
        Response updateResponse = RestAssured
                .given()
                .body(booking.toString())
                .put("/{id}", bookingId);
        updateResponse.then().log().all().statusCode(200);
    }

    @Test
    public void deleteBookingTest() {
        Response bookingsResponse = RestAssured
                .given()
                .get();
        bookingsResponse.then().log().all().statusCode(200);
        JsonPath jsonPath = bookingsResponse.jsonPath();
        Integer bookingId = jsonPath.getInt("[2].bookingid");
        Response deleteResponse = RestAssured
                .given()
                .delete("/{id}}", bookingId);
        deleteResponse.then().log().all().statusCode(201);
    }
}
