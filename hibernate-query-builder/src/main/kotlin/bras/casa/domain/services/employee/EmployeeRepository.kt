/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee

import bras.casa.domain.model.employee.Employee
import bras.casa.domain.services.employee.search.filters.SearchFilter

interface EmployeeRepository {

    fun save(employee: Employee)

    fun find(filter: SearchFilter<Employee>? = null): Employee?

    fun list(filter: SearchFilter<Employee>? = null): List<Employee>

    fun exists(filter: SearchFilter<Employee>? = null): Boolean

    fun count(filter: SearchFilter<Employee>? = null): Long
}
