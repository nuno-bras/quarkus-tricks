# Repository Extension

Sometimes, there is a technical requirement to have the business data stored in different location.
From my experience, one of the requirements is to execute some form of search that is inviable to
execute inside the database. This requirement for performance, often makes engineers look for another
storage engine that can provide that function with much lower development and maintenance costs.

By adding another external dependency to the project, also brings another hidden problem: what should
the application do when the service is unavailable? Can I use the same structure that defines the database
table for this new search engine? How can the product evolve with overtime without paying for the hard coupling?

These the questions that I try to answer with this module. To isolate the technical details from the business.
To decouple different scopes of data inside their own boundaries and lifecycles. To make the business dictate
what it is, not the data behind it.

## Objectives
### 1. Separate the persistence from the domain to ensure the persistence models don't leak to the domain layer
This is achieved by isolating the persistence layer into a single package, with only internal classes.
The persistence classes are located in the persistence package. We have two types of data: [database](src/main/kotlin/bras/casa/infrastructure/persistence/database/ProductEntity.kt)
and [search](src/main/kotlin/bras/casa/infrastructure/persistence/search/SearchableProduct.kt).

### 2. Abstract and isolate the two technical details inside their own package
Both database and search implementations are package internal. No class sharing means no coupling.
Each side can evolve independently without side effects.

### 3. Configuration dictates dependencies
This example shows how can a simple configuration add, or remove, the search engine dependency without affecting
the domain layer. This also means, more services can be added, like caching, without affecting other components.