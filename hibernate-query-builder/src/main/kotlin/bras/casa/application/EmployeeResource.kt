/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.application

import bras.casa.application.responses.EmployeeDTO
import bras.casa.domain.model.employee.EmployeeReference
import bras.casa.domain.usecase.employee.ListEmployeesUseCase
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam

@Path("employee")
class EmployeeResource(private val useCase: ListEmployeesUseCase) {
    @GET
    fun listEmployees(@QueryParam("ref") ref: String, @QueryParam("dept") name: String?) =
        useCase.execute(EmployeeReference(ref), name).map { EmployeeDTO(it) }.toList()
}
