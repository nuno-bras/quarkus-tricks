/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.application.responses

import bras.casa.domain.model.employee.Employee

data class EmployeeDTO(
    val id: String,
    val name: String,
    val reference: String,
    val departmentId: String
) {
    constructor(
        employee: Employee
    ) : this(
        employee.id.toUUID().toString(),
        employee.name,
        employee.reference.toString(),
        employee.department.toString()
    )
}
