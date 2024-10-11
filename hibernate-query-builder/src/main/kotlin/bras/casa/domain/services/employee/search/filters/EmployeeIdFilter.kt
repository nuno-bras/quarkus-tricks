/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import bras.casa.domain.model.employee.Employee
import bras.casa.domain.model.employee.EmployeeId

class EmployeeIdFilter(id: EmployeeId) : EqualSearchFilter<Employee>(id.toUUID()) {}
