/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "DEPARTMENT")
internal class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "ID")
    var id: UUID? = null

    @Column(name = "NAME", nullable = false) lateinit var name: String

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "department",
        targetEntity = EmployeeEntity::class
    )
    lateinit var employees: List<EmployeeEntity>
}
