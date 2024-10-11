/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model.employee

import bras.casa.domain.model.department.DepartmentId

class Employee(
    val id: EmployeeId,
    var name: String,
    var reference: EmployeeReference,
    var department: DepartmentId
) {}
