package treinamentorestassured;

import jsonObjects.CategoryObject;
import jsonObjects.PedidoObject;
import jsonObjects.PetObject;
import jsonObjects.TagObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import utils.Globals;
import utils.JsonUtil;
import java.io.IOException;



public class TreinamentoRestAssured {
    Globals globals = new Globals();

    @Test
    public void adicionarNovoPetaLojaComSucesso(){
        int id = 67894569;;
        int categoryId = 67844569;;
        String categoryName = "cães";
        String name = "cachorro do vander";
        String [] photoUrls = new String [] {"https://petstore.swagger.io/v2/pet/"+id+"/pet1.png","https://petstore.swagger.io/v2/pet/"+id+"/pet2.png"};
        int idTag1 = 67894570;
        String nameTag1 = "macho";
        int idTag2 = 66896580;
        String nameTag2 = "caramelo";
        String status = "available";

        PetObject petObject =new PetObject();
        id = id + 1;
        petObject.setId(id);
        idTag1 = idTag1+1;
        idTag2 = idTag2+1;
        categoryId = categoryId+1;

        CategoryObject categoryObject =new CategoryObject();
        categoryObject.setId(categoryId);
        categoryObject.setName(categoryName);

        petObject.setCategory(categoryObject);
        petObject.setName(name);
        petObject.setPhotoUrls(photoUrls);
        petObject.setStatus(status);

        TagObject tag1 =new TagObject();
        TagObject tag2 =new TagObject();

        tag1.setId(idTag1);
        tag1.setName(nameTag1);
        tag2.setId(idTag2);
        tag2.setName(nameTag2);

        petObject.setTags(new TagObject[]{tag1,tag2});

        given().
                baseUri(globals.getBaseUri()).
                basePath("pet").
                header("content-type", "Application/Json").
                body(petObject).
                when().post().then().statusCode(200).
                body("id", equalTo(id)).
                body("categoryId", equalTo(categoryId)).
                body("tags1.name", equalTo(nameTag2));
        petObject.deletePet(id);
    }

    @Test
    public void cadastrarNovoPedidodePetComSucesso(){

        int id =  888888;
        int petId = 216132;
        int quantity = 1;
        String shipDate = "2024-05-25T19:23:39.096Z";
        String status = "available";
        boolean complete = true;

        PedidoObject pedido = new PedidoObject();
        pedido.setId(id);
        pedido.setPetId(petId);
        pedido.setQuantity(quantity);
        pedido.setShipDate(shipDate);
        pedido.setStatus(status);
        pedido.setComplete(true);

        given().
                baseUri(globals.getBaseUri()).
                basePath("store/order").
                header("content-type", "Application/Json").
                body(pedido).
                when().post().then().statusCode(200).
                        body("id", equalTo(id)).
                        body("petId", equalTo(petId)).
                        body("quantity", equalTo(quantity)).
                        body("shipDate", containsString("2024-05-25")).
                        body("status", equalTo(status)).
                        body("complete", equalTo(complete));
    }

    @Test
    public void pesquisarPorUmPetInexistente(){
        int id = 963852741;
        String type = "error";
        String message = "Pet not found";

        given().
                baseUri(globals.getBaseUri()).
                basePath("pet/{petId}").
                pathParam("petId",id).
                when().get().then().statusCode(404).
                body("type", equalTo(type)).
                body("message", equalTo(message));
    }

    @Test
    public void atualizarDadosDeUmPetExistente(){
        int id = 216132;
        int categoryId = 216132;
        String categoryName = "cães";
        String name = "cachorro do vander";
        String [] photoUrls = new String [] {"https://petstore.swagger.io/v2/pet/"+id+"/pet1.png","https://petstore.swagger.io/v2/pet/"+id+"/pet2.png"};
        int idTag1 = 159359;
        String nameTag1 = "macho";
        int idTag2 = 159368;
        String nameTag2 = "caramelo";
        String status = "available";

        TagObject [] tags = new TagObject[]{};

        //PetObject pet = new PetObject();
        PetObject pet = JsonUtil.readJsonFromFile("PetResource.json", PetObject.class);
        id = id+1;

        //pet.deletePet(id);
        pet.setId(id);
        pet.insertNewPet(pet);

        pet.setName(name);
        given().
                baseUri(globals.getBaseUri()).
                basePath("/pet").
                header(globals.getContent()).
                body(pet).
                when().put().
                then().statusCode(200).
                body("name", equalTo(name));

        pet.deletePet(id);
    }

    @Test
    public void pesquisarPorUmPetInformandoIdComFormatoInválido(){
        String id = "999999999999999999999";
        given().
                baseUri(globals.getBaseUri()).
                basePath("/pet/"+id).
                header(globals.getContent()).
                when().get().
                then().statusCode(404).
                body("message", containsString("java.lang.NumberFormatException: For input string:"));
    }

    @Test
    public void pesquisarPorPetsComStatusPending(){
        String status = "pending";
        given().
                baseUri(globals.getBaseUri()).
                basePath("/pet/findByStatus?status=" + status).
                header(globals.getContent()).
                when().get().
                then().
                statusCode(200).body(hasItems());
    }

}
