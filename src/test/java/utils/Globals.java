package utils;

import io.restassured.http.Header;

public class Globals {
    public String baseUri = "https://petstore.swagger.io/v2";

    public String content = "application/json";

    public String getBaseUri() {
        return baseUri;
    }

    public Header getContent() {
        Header header = new Header("Content-Type", content);
        return header;
    }
}
