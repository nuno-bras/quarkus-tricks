/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.usecase.employee

import bras.casa.domain.model.department.DepartmentId
import bras.casa.domain.model.employee.Employee
import bras.casa.domain.model.employee.EmployeeId
import bras.casa.domain.model.employee.EmployeeReference
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import java.util.UUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
class ListEmployeesUseCaseTest {

    @Inject private lateinit var useCase: ListEmployeesUseCase

    @Test
    fun `Search for employees by a reference which expects only return one result`() {
        val employeeList: List<Employee> = useCase.execute(EmployeeReference("empl1"))
        assertEquals(1, employeeList.size)
        val employee = employeeList[0]
        assertEquals(
            EmployeeId(UUID.fromString("01927c78-99c2-7b28-8d54-9ad48dab0344")),
            employee.id
        )
        assertEquals("employee 1", employee.name)
        assertEquals(EmployeeReference("empl1"), employee.reference)
        assertEquals(
            DepartmentId(UUID.fromString("01927c77-c915-7b2a-92c8-2a597d47e29a")),
            employee.department
        )
    }

    @Test
    fun `Search for employees by a reference and department name which expects only return one result`() {
        val employeeList: List<Employee> =
            useCase.execute(EmployeeReference("empl1"), "department 1")
        assertEquals(1, employeeList.size)
        val employee = employeeList[0]
        assertEquals(
            EmployeeId(UUID.fromString("01927c78-99c2-7b28-8d54-9ad48dab0344")),
            employee.id
        )
        assertEquals("employee 1", employee.name)
        assertEquals(EmployeeReference("empl1"), employee.reference)
        assertEquals(
            DepartmentId(UUID.fromString("01927c77-c915-7b2a-92c8-2a597d47e29a")),
            employee.department
        )
    }

    @Test
    fun `Search for employees by a non existing reference which returns zero results`() {
        val employeeList: List<Employee> = useCase.execute(EmployeeReference("empl0"))
        assertTrue(employeeList.isEmpty())
    }

    @Test
    fun `Search for employees by a non existing reference and valid department name which returns zero results`() {
        val employeeList: List<Employee> =
            useCase.execute(EmployeeReference("empl0"), "department 1")
        assertTrue(employeeList.isEmpty())
    }
}
