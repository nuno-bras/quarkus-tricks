# Hibernate Query Builder

One of the commons problems that I see in the repository pattern, is the excessive 
methods added to the repository in order to search data within a bounded context (
a single table, or a group of closely related tables).

Most of the times, this can be simplified by providing a better API to define the search
filters as a method argument, and keep the repository implementation open to any query 
command received.

## Objectives
### 1. Separate the persistence from the domain to ensure the database model doesn't leak to the domain layer
This is achieved by isolating the persistence layer into a single package, with only internal classes.
The persistence classes are located in the [persistence](src/main/kotlin/bras/casa/infrastructure/persistence) package.

The domain classes, are in the [domain](src/main/kotlin/bras/casa/domain) package. Only the employee
implements the [filters](src/main/kotlin/bras/casa/domain/services/employee/search/filters). The Department entity purpose is to provide an example where we search for a
value located in another table, since each entity is persisted into their own tables.


### 2. Define the query filters at the domain layer
The filters can be grouped in 3 types:
1. Abstractions ([SearchFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/SearchFilter.kt), [AndSearchFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/AndSearchFilter.kt), [OrSearchFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/OrSearchFilter.kt), [EqualSearchFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/EqualSearchFilter.kt))
2. Visitor abstraction to mount the persistence context to the domain ([SearchFilterParser.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/SearchFilterParser.kt)) (implemented by [EmployeeSearchFilterParser.kt](src/main/kotlin/bras/casa/infrastructure/persistence/EmployeeSearchFilterParser.kt))
3. Filters definitions ([EmployeeIdFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/EmployeeIdFilter.kt), [EmployeeNameFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/EmployeeNameFilter.kt), [EmployeeReferenceFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/EmployeeReferenceFilter.kt), [EmployeeDepartmentNameFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/EmployeeDepartmentNameFilter.kt))

### 3. The filters can be combined in any order, and by different rules.
Looking at the abstractions, we can observe the [SearchFilter.kt](src/main/kotlin/bras/casa/domain/services/employee/search/filters/SearchFilter.kt) is the root of all extensions,
and implements methods to add two filters (`AND`, and `OR` operators). This can be extended to add more operators, like the `IN`, `NOT`, etc.
