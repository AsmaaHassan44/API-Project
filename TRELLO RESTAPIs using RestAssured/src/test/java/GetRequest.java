import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequest {
    private RequestSpecification requestSpecification;
    private Response response;
    static String key = "012c103ff64a22bb740c09c085163b6b";
    static String token ="ATTAbbc7d45f1da4399485ff1485bf63c6eb7f6f011083d5d4651df21044dcb78d0dF32288F0";
    static String base_uri="https://api.trello.com";
    public GetRequest(String url, String path){
        requestSpecification=RestAssured.given().baseUri(url).basePath(path);
    }
    public void addHeader(String key, String value){
        this.requestSpecification.header(key,value);
    }
    public void addQueryParameter(String key, String value){
        this.requestSpecification.queryParam(key,value);
    }
    public void send(){
        response= requestSpecification.when().get();
    }

    public static void main(String[] args) {
        GetRequest get= new GetRequest(base_uri,"/1/members/me");
        get.addQueryParameter("key",key);
        get.addQueryParameter("token",token);
        get.send();
        get.response.prettyPrint();
    }
}
