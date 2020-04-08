# TelstraTestApp

TelstraTestApp

The app is built using Kotlin and the architecture of the app is built around Android Architecture Components.

The Activities and Fragments are free of business logic and it is mostly moved to ViewModels. The data is observed using LiveData and the Data Binding Library is used to bind UI components in layouts to the app's data sources.

A Repository layer is used for handling data operations. The repository abstracts the actual source of data from the consumers. The actual source of data is an implementation of a DataSource interface. Currently the implementation of the interface used is RemoteDataSource which gets data from a server.

Dagger2 has been used for dependency injection. All the dependencies for the components have been injected so that the components could be tested by injecting mocks for the dependencies.

Tests have been implemented using JUnit and Mockito for unit testing.
