/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence

import bras.casa.domain.model.department.DepartmentId
import bras.casa.domain.model.employee.Employee
import bras.casa.domain.model.employee.EmployeeId
import bras.casa.domain.model.employee.EmployeeReference
import bras.casa.domain.services.employee.EmployeeRepository
import bras.casa.domain.services.employee.search.filters.SearchFilter
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceException
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import jakarta.transaction.Transactional

@ApplicationScoped
internal class PgEmployeeRepository(val em: EntityManager) : EmployeeRepository {

    @Transactional
    override fun save(employee: Employee) {
        val department: DepartmentEntity =
            em.find(DepartmentEntity::class.java, employee.department.toUUID())
        val entity =
            EmployeeEntity(
                employee.id.toUUID(),
                employee.name,
                employee.reference.toString(),
                department
            )
        em.persist(entity)
    }

    override fun find(filter: SearchFilter<Employee>?): Employee? {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<EmployeeEntity> = cb.createQuery(EmployeeEntity::class.java)
        val root: Root<EmployeeEntity> = query.from(EmployeeEntity::class.java)

        try {
            val entity: EmployeeEntity =
                em.createQuery(
                        query
                            .select(root)
                            .where(filter?.apply(EmployeeSearchFilterParser(cb, root)))
                    )
                    .singleResult
            return mapEntityToModel(entity)
        } catch (_: PersistenceException) {
            return null
        }
    }

    override fun list(filter: SearchFilter<Employee>?): List<Employee> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<EmployeeEntity> = cb.createQuery(EmployeeEntity::class.java)
        val root: Root<EmployeeEntity> = query.from(EmployeeEntity::class.java)

        return em.createQuery(
                query.select(root).where(filter?.apply(EmployeeSearchFilterParser(cb, root)))
            )
            .resultStream
            .map { mapEntityToModel(it) }
            .toList()
    }

    override fun exists(filter: SearchFilter<Employee>?): Boolean = count(filter) > 0

    override fun count(filter: SearchFilter<Employee>?): Long {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<Long> = cb.createQuery(Long::class.java)
        val root: Root<EmployeeEntity> = query.from(EmployeeEntity::class.java)

        return em.createQuery(
                query
                    .select(cb.count(root))
                    .where(filter?.apply(EmployeeSearchFilterParser(cb, root)))
            )
            .singleResult
    }

    private fun mapEntityToModel(entity: EmployeeEntity) =
        Employee(
            EmployeeId(entity.id!!),
            entity.name,
            EmployeeReference(entity.reference),
            DepartmentId(entity.department.id!!)
        )
}
