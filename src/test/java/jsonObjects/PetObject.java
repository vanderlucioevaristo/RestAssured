package jsonObjects;
import com.fasterxml.jackson.*; //databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetObject {
    int id;
    CategoryObject category;
    String name;
    String[] photoUrls;
    TagObject[] tags;
    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryObject getCategory() {
        return category;
    }

    public void setCategory(CategoryObject category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

    public TagObject[] getTags() {
        return tags;
    }

    public void setTags(TagObject[] tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void insertNewPet(PetObject newPet){
             given().
                    baseUri("https://petstore.swagger.io/v2/").
                    basePath("pet").
                    header("content-type", "Application/Json").
                    body(newPet).when().post().then().statusCode(200).body("id", equalTo(newPet.getId()));
    }

    public void deletePet(int id){
        given().
                baseUri("https://petstore.swagger.io/v2").
                basePath("/pet/" +id).
                header("content-type","Application/Json").delete().
                then().statusCode(200);
    }
}
