/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.domain.usecase.employee

import bras.casa.domain.model.employee.Employee
import bras.casa.domain.model.employee.EmployeeReference
import bras.casa.domain.services.employee.EmployeeRepository
import bras.casa.domain.services.employee.search.filters.EmployeeDepartmentNameFilter
import bras.casa.domain.services.employee.search.filters.EmployeeReferenceFilter
import bras.casa.domain.services.employee.search.filters.SearchFilter
import jakarta.enterprise.context.RequestScoped

@RequestScoped
class ListEmployeesUseCase(private val repository: EmployeeRepository) {

    fun execute(reference: EmployeeReference, departmentName: String? = null): List<Employee> {

        var searchFilter: SearchFilter<Employee> = EmployeeReferenceFilter(reference)
        departmentName?.let { searchFilter = searchFilter.and(EmployeeDepartmentNameFilter(it)) }

        return repository.list(searchFilter)
    }
}
