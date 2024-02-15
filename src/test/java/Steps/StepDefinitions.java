package Steps;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private String baseUrl = "https://jsonplaceholder.typicode.com/users";
    private Response response;


    @Given("a base URL {string}")
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    @When("realizar uma requisição GET")
    public void realizarRequisicaoGET() {
        this.response = given().baseUri(baseUrl).when().get();
    }

    @When("realizar uma requisição GET para {string}")
    public void realizarRequisicaoGETComId(String endpoint) {
        this.response = given().baseUri(baseUrl).pathParam("id", endpoint).when().get("/{id}"); // Adicione {id} para incluir o parâmetro no caminho
    }

    @Then("o código de status deve ser {int}")
    public void verificarStatusCode(int statusCode) {
        this.response.then().statusCode(statusCode);
    }

    @Then("o corpo da resposta deve conter {string}")
    public void verificarCorpoResposta(String expectedString) {
        this.response.then().body(containsString(expectedString));
    }

    @Then("o corpo da resposta deve conter o JSON lido de {string}")
    public void verificarCorpoRespostaComJson(String jsonFilePath) throws Exception {
        // Lê o conteúdo do arquivo JSON esperado
        String expectedJson = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // Obtém o corpo da resposta como uma string
        String responseBody = response.getBody().asString();

        // Converte a string do corpo da resposta para um objeto JsonNode usando o Jackson
        ObjectMapper objectMapper = new ObjectMapper() {
            @Override
            public Object deserialize(ObjectMapperDeserializationContext objectMapperDeserializationContext) {
                return null;
            }

            @Override
            public Object serialize(ObjectMapperSerializationContext objectMapperSerializationContext) {
                return null;
            }
        };
        boolean actualJson;
        actualJson = objectMapper.equals(responseBody);

        // Converte a string do JSON esperado para um objeto JsonNode usando o Jackson
        boolean expectedJsonNode;
        expectedJsonNode = objectMapper.equals(expectedJson);

        // Comparação dos objetos JsonNode
        assertEquals(expectedJsonNode, actualJson);
    }

    @Given("um corpo de requisição lido de {string}")
    public void setRequestBody(String jsonFilePath) throws Exception {
        // Lê o conteúdo do arquivo JSON
        String requestBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // Define o corpo da requisição
        given().body(requestBody);
    }

    @When("realizar uma requisição POST")
    public void realizarRequisicaoPOST() {
        this.response = given().baseUri(baseUrl).header("Content-Type", "application/json").post();
    }

    @When("realizar uma requisição PUT para {string} com o corpo da requisição {string}")
    public void realizarRequisicaoPUTComCorpo(String endpoint, String requestBody) {
        this.response = given().baseUri(baseUrl).pathParam("id", endpoint).header("Content-Type", "application/json").body(requestBody).when().put("/{id}");
    }
    @When("realizar uma requisição GET para {string} com a query-string {string}")
    public void realizarRequisicaoGETComQueryString(String endpoint, String queryString) {
        this.response = given().baseUri(baseUrl)
                .pathParam("id", endpoint)
                .queryParam("example", queryString)
                .when()
                .get("/{id}")
                .then()
                .extract().response();
    }

    @Then("o campo {string} no corpo da resposta deve ser igual a {int}")
    public void verificarCampoNoCorpoResposta(String campo, Integer valorEsperado) {
        this.response.then().body(campo, equalTo(valorEsperado));
    }

    @Then("o campo {string} no corpo da resposta deve ser igual a {string}")
    public void verificarCampoNoCorpoResposta(String campo, String valorEsperado) {
        this.response.then().body(campo, equalTo(valorEsperado));
    }

}