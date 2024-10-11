/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.services.employee.search.filters

import bras.casa.domain.model.employee.Employee
import bras.casa.domain.model.employee.EmployeeReference

class EmployeeReferenceFilter(ref: EmployeeReference) :
    EqualSearchFilter<Employee>(ref.toString()) {}
