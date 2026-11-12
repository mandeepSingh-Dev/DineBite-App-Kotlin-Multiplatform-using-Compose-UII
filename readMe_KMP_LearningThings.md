# Koin Dependency Injection in Kotlin Multiplatform (KMP)

A practical guide to implementing Dependency Injection using **Koin** in Kotlin Multiplatform projects.

This guide covers:

- Koin setup in KMP
- Creating separate DI modules
- Registering API services
- Registering repositories
- Registering UseCases
- Registering ViewModels
- Injecting ViewModels using `koinViewModel()`
- Using injected ViewModels inside Compose Multiplatform screens
- Sharing dependencies across Android and iOS

---

## 1. What is Koin?

Koin is a lightweight Dependency Injection framework for Kotlin and Kotlin Multiplatform.

It helps us manage dependencies between different classes without manually creating their objects.

For example, consider the following dependency chain:

```text
LoginScreen
    |
    v
LoginViewModel
    |
    v
LoginUseCase
    |
    v
UserRepository
    |
    v
ApiService
```

Instead of manually creating every object, Koin automatically resolves and provides the required dependencies.

### Android Hilt vs Koin

| Dagger-Hilt | Koin |
|---|---|
| @Inject | get() / constructor resolution |
| @Module | module {} |
| @Provides | single {} |
| @Singleton | single {} |
| @Binds | bind() / interface binding |
| @HiltViewModel | viewModel {} |
| @AndroidEntryPoint | Koin integration |
| hiltViewModel() | koinViewModel() |

---

## 2. Add Koin Dependencies

Add the following dependencies to your `composeApp/build.gradle.kts`.

```kotlin
commonMain.dependencies {

    // Koin core
    implementation("io.insert-koin:koin-core:<version>")

    // Koin ViewModel
    implementation("io.insert-koin:koin-compose-viewmodel:<version>")

    // Koin Compose Multiplatform
    implementation("io.insert-koin:koin-compose:<version>")
}
```

Use compatible versions from the official Koin documentation:

https://insert-koin.io/docs/setup/koin/

---

## 3. Recommended Project Structure

```text
composeApp/
│
└── src/
    │
    ├── commonMain/
    │   ├── kotlin/
    │   │
    │   ├── di/
    │   │   ├── AppModule.kt
    │   │   ├── ApiModule.kt
    │   │   ├── RepositoryModule.kt
    │   │   ├── UseCaseModule.kt
    │   │   └── ViewModelModule.kt
    │   │
    │   ├── data/
    │   │   ├── remote/
    │   │   │   └── ApiService.kt
    │   │   │
    │   │   └── repository/
    │   │       └── UserRepositoryImpl.kt
    │   │
    │   ├── domain/
    │   │   ├── model/
    │   │   │   └── User.kt
    │   │   │
    │   │   ├── repository/
    │   │   │   └── UserRepository.kt
    │   │   │
    │   │   └── usecase/
    │   │       └── LoginUseCase.kt
    │   │
    │   └── presentation/
    │       └── login/
    │           ├── LoginViewModel.kt
    │           └── LoginScreen.kt
    │
    ├── androidMain/
    │   └── kotlin/
    │
    └── iosMain/
        └── kotlin/
```

---

## 4. Create API Module

First, create your API service.

For this example, we will use Ktor.

### ApiService.kt

```kotlin
class ApiService(
    private val client: HttpClient
) {

    suspend fun login(
        email: String,
        password: String
    ): User {

        return client.post(
            "https://api.example.com/login"
        ) {
            contentType(ContentType.Application.Json)

            setBody(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
        }.body()
    }
}
```

### ApiModule.kt

```kotlin
val apiModule = module {

    single {
        createHttpClient()
    }

    single {
        ApiService(get())
    }
}
```

Here:

- `single {}` creates a singleton dependency.
- `get()` resolves the required dependency.
- `ApiService` receives the registered `HttpClient`.

---

## 5. Create Repository Module

The repository communicates with the API service.

### UserRepository.kt

```kotlin
interface UserRepository {

    suspend fun login(
        email: String,
        password: String
    ): User
}
```

### UserRepositoryImpl.kt

```kotlin
class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun login(
        email: String,
        password: String
    ): User {

        return apiService.login(
            email,
            password
        )
    }
}
```

### RepositoryModule.kt

```kotlin
val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}
```

Important:

`get()` automatically resolves the `ApiService` registered in `apiModule`.

Koin modules are not isolated. Dependencies can be resolved across all modules loaded into the same Koin application.

---

## 6. Create UseCase Module

UseCases contain business logic.

### LoginUseCase.kt

```kotlin
class LoginUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): User {

        return repository.login(
            email,
            password
        )
    }
}
```

### UseCaseModule.kt

```kotlin
val useCaseModule = module {

    factory {
        LoginUseCase(get())
    }
}
```

Why `factory`?

Every time Koin resolves this dependency, it creates a new instance.

For stateless UseCases, this is a reasonable default.

---

## 7. Create ViewModel Module

This is where we register the ViewModel.

### LoginViewModel.kt

```kotlin
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<LoginState>(LoginState.Idle)

    val loginState = _loginState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _loginState.value = LoginState.Loading

            try {

                val user = loginUseCase(
                    email,
                    password
                )

                _loginState.value =
                    LoginState.Success(user)

            } catch (e: Exception) {

                _loginState.value =
                    LoginState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }
}
```

### LoginState.kt

```kotlin
sealed interface LoginState {

    data object Idle : LoginState

    data object Loading : LoginState

    data class Success(
        val user: User
    ) : LoginState

    data class Error(
        val message: String
    ) : LoginState
}
```

### ViewModelModule.kt

```kotlin
val viewModelModule = module {

    viewModel {
        LoginViewModel(get())
    }
}
```

Important:

- `viewModel {}` registers a ViewModel with Koin's ViewModel lifecycle integration.
- `get()` resolves `LoginUseCase`.
- Koin automatically provides the ViewModel to the Compose screen.
- You don't need to manually instantiate the ViewModel.

---

## 8. Combine All Modules

Create `AppModule.kt`.

```kotlin
val appModules = listOf(

    apiModule,

    repositoryModule,

    useCaseModule,

    viewModelModule

)
```

Now initialize Koin.

### initKoin.kt

```kotlin
fun initKoin() {

    startKoin {

        modules(appModules)

    }
}
```

All modules are registered in the same Koin application.

The dependency resolution chain becomes:

```text
viewModelModule
      |
      v
LoginViewModel
      |
      v
useCaseModule
      |
      v
LoginUseCase
      |
      v
repositoryModule
      |
      v
UserRepositoryImpl
      |
      v
apiModule
      |
      v
ApiService
      |
      v
HttpClient
```

---

## 9. Use ViewModel in LoginScreen

Now comes the most important part.

Instead of manually creating the ViewModel:

```kotlin
val viewModel = LoginViewModel(...)
```

We use `koinViewModel()`.

### LoginScreen.kt

```kotlin
@Composable
fun LoginScreen(

    viewModel: LoginViewModel = koinViewModel()

) {

    val loginState by viewModel.loginState.collectAsState()

    Column {

        Button(

            onClick = {

                viewModel.login(
                    email = "test@gmail.com",
                    password = "123456"
                )

            }

        ) {

            Text("Login")

        }

        when (val state = loginState) {

            LoginState.Idle -> {
                Text("Please login")
            }

            LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is LoginState.Success -> {
                Text("Welcome ${state.user.name}")
            }

            is LoginState.Error -> {
                Text(state.message)
            }
        }
    }
}
```

### How does koinViewModel() work?

```kotlin
viewModel: LoginViewModel = koinViewModel()
```

Behind the scenes:

1. Compose requests a `LoginViewModel`.
2. Koin checks its registered ViewModel definitions.
3. Koin finds `LoginViewModel` in `viewModelModule`.
4. Koin resolves `LoginUseCase`.
5. Koin resolves `UserRepository`.
6. Koin resolves `ApiService`.
7. The fully constructed ViewModel is provided to the screen.

You don't need to write the dependency creation logic inside LoginScreen.

---

## 10. Initialize Koin in Compose Multiplatform

You must initialize Koin before requesting dependencies.

For a shared Compose application, you can initialize it in your root composable:

```kotlin
@Composable
fun App() {

    KoinApplication(
        application = {
            modules(appModules)
        }
    ) {

        AppNavigation()

    }
}
```

Now all screens inside this Koin application can access the registered dependencies.

For this approach, do not also call `startKoin()` for the same application instance.

Alternatively, you can initialize Koin once from your platform entry point and use the existing global Koin context.

---

## 11. Understanding single(), factory() and viewModel()

These are three important Koin definitions.

| Definition | Behavior | Example |
|---|---|---|
| `single {}` | One instance per Koin container | ApiService |
| `factory {}` | New instance each time requested | UseCase |
| `viewModel {}` | ViewModel with lifecycle-aware resolution | LoginViewModel |

Example:

```kotlin
val appModule = module {

    // One shared instance
    single {
        ApiService(get())
    }

    // New instance whenever resolved
    factory {
        LoginUseCase(get())
    }

    // Lifecycle-aware ViewModel
    viewModel {
        LoginViewModel(get())
    }
}
```

---

## 12. Important: Sharing Koin Across Android and iOS

Koin and the dependency definitions can live in `commonMain`.

Your API service, repository, UseCase and ViewModel can also live in `commonMain`.

For platform-specific networking, provide the appropriate Ktor engine.

For example:

```kotlin
// androidMain

fun createHttpClient() = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
}
```

```kotlin
// iosMain

fun createHttpClient() = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json()
    }
}
```

You can expose the platform-specific client creation through an `expect`/`actual` function or inject a platform-provided HttpClient.

The rest of your networking, repository and ViewModel code remains shared.

---

## 13. Final Architecture

```text
                 LoginScreen
                      |
                      v
               koinViewModel()
                      |
                      v
                LoginViewModel
                      |
                      v
                 LoginUseCase
                      |
                      v
                UserRepository
                      |
                      v
                 ApiService
                      |
                      v
                   Ktor
                      |
                Backend API
```

Koin manages the dependencies, Ktor handles networking, repositories coordinate data access, UseCases contain business logic, and ViewModels manage UI state.

---

## Official Documentation

- Koin: https://insert-koin.io/docs/intro/
- Koin Compose: https://insert-koin.io/docs/reference/koin-compose/compose/
- Ktor: https://ktor.io/docs/client-create-and-configure.html
- Kotlin Multiplatform: https://kotlinlang.org/docs/multiplatform.html

**Conclusion:** Koin allows you to organize dependency registration into multiple independent modules while resolving dependencies across all modules loaded into the same application. This makes it suitable for modular MVVM and Clean Architecture in Kotlin Multiplatform.
