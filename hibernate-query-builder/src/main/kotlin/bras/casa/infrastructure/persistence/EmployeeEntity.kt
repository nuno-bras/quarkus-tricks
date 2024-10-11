/**
 * Development by Nuno Brás, 2024
 */
package bras.casa.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "EMPLOYEE")
internal class EmployeeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "ID")
    var id: UUID? = null

    @Column(name = "NAME", nullable = false) lateinit var name: String

    @Column(name = "REFERENCE", unique = true, nullable = false) lateinit var reference: String

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "DEPARTMENT_ID",
        nullable = false,
        foreignKey =
            ForeignKey(
                name = "FK_EMPLOYEE_DEPARTMENT_ID",
                foreignKeyDefinition =
                    "FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(ID) ON DELETE CASCADE"
            )
    )
    lateinit var department: DepartmentEntity

    constructor(
        id: UUID,
        name: String,
        reference: String,
        departmentEntity: DepartmentEntity
    ) : this() {
        this.id = id
        this.name = name
        this.reference = reference
        this.department = departmentEntity
    }
}
