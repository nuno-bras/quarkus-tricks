/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.model.department

import bras.casa.domain.model.employee.Employee

class Department(val id: DepartmentId, var name: String, var employees: MutableSet<Employee>) {}
