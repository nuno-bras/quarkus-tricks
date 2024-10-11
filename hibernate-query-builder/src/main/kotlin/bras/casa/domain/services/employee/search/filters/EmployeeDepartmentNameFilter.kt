/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import bras.casa.domain.model.employee.Employee

class EmployeeDepartmentNameFilter(name: String) : EqualSearchFilter<Employee>(name) {}
