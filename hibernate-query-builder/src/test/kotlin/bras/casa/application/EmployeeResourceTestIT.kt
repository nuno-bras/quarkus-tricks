/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.application

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class EmployeeResourceTestIT {

    @Test
    fun `No filters when searching for employees then list the only employee with that reference`() {
        val response =
            given()
                .`when`()
                .queryParam("ref", "empl1")
                .get("/employee")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asPrettyString()

        assertEquals(
            """[
    {
        "id": "01927c78-99c2-7b28-8d54-9ad48dab0344",
        "name": "employee 1",
        "reference": "empl1",
        "departmentId": "DepartmentId(id=01927c77-c915-7b2a-92c8-2a597d47e29a)"
    }
]""",
            response
        )
    }
}
