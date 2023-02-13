import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class TrelloAPITesting {
    static String key = "012c103ff64a22bb740c09c085163b6b";
    static String token ="ATTAbbc7d45f1da4399485ff1485bf63c6eb7f6f011083d5d4651df21044dcb78d0dF32288F0";
    static String base_uri="https://api.trello.com";
    static String memberID="";
    static String organizationID="";
    static String boardID="";
    static String listID="";


    public static void main(String[] args) {
        checkAuthority();
        createNewOrganization();
        getMemberID();
        getOrganizationsForMember();
        createBoardInsideOrganization();
        getBoardsInOrganization();
        createListInsideBoard();
        getListsInBoard();
        archiveList();
        deleteBoard();
        deleteOrganization();

    }
    ///////////////////////////////////////////////////////////////////
    public static void checkAuthority(){
        System.out.println("Test Case: check Authority - invalid token scenario");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/me")
                .when()
                .get();
        response.then()
                .statusCode(400)
                .and()
                .statusLine("HTTP/1.1 400 Bad Request")
                .and()
                .body(Matchers.equalTo("invalid token"));
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("The response body is ");
        response.prettyPrint();
        System.out.println("as expected");
        System.out.println("-------");
    }
/////////////////////////////////////////////////////////////////////////
    public static void createNewOrganization() {
        System.out.println("Test Case: create a New Organization");
        RequestSpecification rs = RestAssured
                .given()
                .baseUri(base_uri+"/1/organizations?")
                .queryParam("displayName","Mobile Testing Again")
                .queryParam("key",key)
                .queryParam("token",token)
                .header("Content-Type","application/json")
                .header("Accept","*/*");
        Response response= rs
                .when()
                .post();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        organizationID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New organization is created successfully with id "+ organizationID);
        System.out.println("-----------");
    }
    //////////////////////////////////////////////////////////////
    public static void getMemberID() {
        System.out.println("Test Case: check Authority - valid key and token scenario");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/me")
                .queryParam("key",key)
                .queryParam("token",token)
                //sending the request and receiving its response
                .when()
                .get();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        memberID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("The member id is "+ memberID);
        System.out.println("-------------");
    }
////////////////////////////////////////////////////////////
    public static void getOrganizationsForMember() {
        System.out.println("Test Case: get Organizations for Member with memberID");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/"+memberID+"/organizations")
                .queryParam("key",key)
                .queryParam("token",token)
                .when()
                .get();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("------------");
    }
    ////////////////////////////////////
    public static void createBoardInsideOrganization(){
        System.out.println("Test Case: create Board inside Organization with ID organizationID");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/")
                .header("Content-Type","application/json; charset=utf-8")
                .header("Accept","application/json")
                //.header("Accept-Encoding","gzip, deflate, br")
                .queryParam("key",key)
                .queryParam("token",token)
                .queryParam("name","Mobile Testing")
                .queryParam("defaultLists","false")
                .queryParam("idOrganization",""+organizationID)
                .when()
                .post();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        boardID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New board is created successfully with id "+ boardID);
        System.out.println("---------");
    }
    /////////////////////////////////////////
    public static void getBoardsInOrganization() {
        System.out.println("Test Case: get Boards in an Organization with organizationID");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/organizations/"+organizationID+"/boards")
                .queryParam("key",key)
                .queryParam("token",token)
                .when()
                .get();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------");
    }
///////////////////////////////////////////////////////////////////////////////
    public static void createListInsideBoard(){
        System.out.println("Test Case: create a list inside a Board with boardID");
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/lists?")
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .queryParam("name","ToDo")
                .queryParam("idBoard",boardID)
                .queryParam("key",key)
                .queryParam("token",token);
        Response response = rs
               .when()
               .post();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        listID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New list is created successfully with id "+ listID);
        System.out.println("--------");
    }
   ////////////////////////////////////////////
    public static void getListsInBoard() {
        System.out.println("Test Case: get Lists In a Board");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/"+boardID+"/lists")
                .param("key",key)
                .param("token",token)
                .when()
                .get();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("---------------");
    }
///////////////////////////////////////////////
    public static void archiveList(){
        System.out.println("Test Case: archive List");
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/lists/"+listID+"/closed?")
                .queryParam("key",key)
                .queryParam("token",token)
                .queryParam("value","true")
                .when()
                .put();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("---------------");
    }
   ///////////////////////////////////////////////////////////////
    public static void deleteBoard(){
        System.out.println("Test Case: delete a Board with board ID "+boardID);
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/"+boardID)
                .queryParam("key",key)
                .queryParam("token",token);
        Response response = rs
                .when()
                .delete();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("--------------");
    }
    public static void deleteOrganization(){
        System.out.println("Test Case: delete an Organization with organization ID "+organizationID);
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/organizations/"+organizationID)
                .queryParam("key",key)
                .queryParam("token",token);
        Response response = rs
                .when()
                .delete();
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------");
    }
}

