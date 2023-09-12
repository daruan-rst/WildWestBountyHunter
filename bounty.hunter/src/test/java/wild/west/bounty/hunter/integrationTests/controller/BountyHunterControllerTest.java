package wild.west.bounty.hunter.integrationTests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import wild.west.bounty.hunter.configs.TestConfigs;
import wild.west.bounty.hunter.integrationTests.AbstractIntegrationTest;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.model.enums.Reputation;
import wild.west.bounty.hunter.wrappedModel.WrappedBountyHunter;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static wild.west.bounty.hunter.model.enums.Reputation.BLOODTHIRTY;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BountyHunterControllerTest extends AbstractIntegrationTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    public void testFindAll() throws JsonProcessingException {

        RequestSpecification specification = new RequestSpecBuilder()
                .setBasePath("api/bounty-hunter/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .build();

        var content = given()
                .spec(specification)
                .contentType("application/json")
                .queryParams("page",0,"size",10,"direction","asc") // requestParams -> neste caso, paginação
//                .header() pode ser colocado qualquer parâmetro de header
                .when()
                .get()
                .then()
                .statusCode(200) // -> assert em cima do status que esperamos receber
                .extract()
                .body()
                .asString();

        assertNotNull(content);

         WrappedBountyHunter bountyHunterWrapped = objectMapper.readValue(content,WrappedBountyHunter.class);

        List<BountyHunter> bountyHunterList = bountyHunterWrapped.getEmbedded().getBountyHunterList();
        BountyHunter lastBountyHunter = bountyHunterList.get(bountyHunterList.size()-1);

        Assertions.assertEquals("Simone dos prazeres", lastBountyHunter.getCowboyName());
        Assertions.assertEquals("Lapa", lastBountyHunter.getOrigin());
        Assertions.assertEquals("BLOODTHIRTY", lastBountyHunter.getReputation());


    }
}
