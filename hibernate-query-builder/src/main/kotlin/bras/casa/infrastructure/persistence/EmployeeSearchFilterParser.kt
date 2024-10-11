/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence

import bras.casa.domain.model.employee.Employee
import bras.casa.domain.services.employee.search.filters.AndSearchFilter
import bras.casa.domain.services.employee.search.filters.EmployeeDepartmentNameFilter
import bras.casa.domain.services.employee.search.filters.EmployeeIdFilter
import bras.casa.domain.services.employee.search.filters.EmployeeNameFilter
import bras.casa.domain.services.employee.search.filters.EmployeeReferenceFilter
import bras.casa.domain.services.employee.search.filters.EqualSearchFilter
import bras.casa.domain.services.employee.search.filters.OrSearchFilter
import bras.casa.domain.services.employee.search.filters.SearchFilterParser
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

internal class EmployeeSearchFilterParser(
    private val cb: CriteriaBuilder,
    private val root: Root<EmployeeEntity>
) : SearchFilterParser<Employee> {

    override fun apply(filter: AndSearchFilter<Employee>): Predicate =
        cb.and(filter.left.apply(this), filter.right.apply(this))

    override fun apply(filter: OrSearchFilter<Employee>): Predicate =
        cb.or(filter.left.apply(this), filter.right.apply(this))

    override fun apply(filter: EqualSearchFilter<Employee>): Predicate {

        val path: Path<Any> =
            when (filter) {
                is EmployeeIdFilter -> root.get("id")
                is EmployeeNameFilter -> root.get("name")
                is EmployeeReferenceFilter -> root.get("reference")
                is EmployeeDepartmentNameFilter -> root.get<Any?>("department").get("name")
            }

        return cb.equal(path, filter.value)
    }
}
