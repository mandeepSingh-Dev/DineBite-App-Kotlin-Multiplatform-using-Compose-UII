# KMP app foundation

This project shares Compose UI and business/data logic between Android and iOS. Platform entry points stay thin; feature behavior lives in `shared/src/commonMain`.

## Layers

For each feature, use this dependency direction:

```
Compose Screen -> ViewModel -> Repository interface -> Repository implementation -> Ktor API/data source
                                         |
                                    domain models
```

- **Screen** renders state and forwards user actions. Keep it stateless where practical.
- **ViewModel** owns screen state and launches work in `viewModelScope`; expose immutable `StateFlow<UiState<T>>`.
- **Repository** owns the source-of-truth and combines remote/local data. ViewModels depend on repository interfaces, not Ktor.
- **Remote data source** contains endpoint calls and DTOs. Map DTOs to domain models at the repository boundary.
- **Local data source** is added when a feature needs durable offline data. Keep database types out of UI and domain layers.

## Networking

`core/network/HttpClientFactory.kt` configures a shared Ktor client with JSON, timeouts, request logging, a bearer-token hook, and Ktor's in-memory HTTP cache. Change the placeholder API base URL before making real requests.

Use typed `@Serializable` DTOs and `apiCall { ... }` at the remote data-source boundary. Convert `ApiResult` into screen state in the repository or ViewModel. Keep endpoint paths and request/response DTOs feature-specific.

- Add common Ktor plugins in `commonMain`; Android's engine is in `androidMain`, iOS's Darwin engine in `iosMain`.
- Add auth refresh/retry as a deliberate auth feature. The current `TokenProvider` only attaches a token; it does not refresh one.
- Logging is set to INFO to avoid request/response bodies and auth headers. Never log credentials or personal data.
- Ktor's cache is in-memory and follows HTTP cache headers. Use a database such as SQLDelight/Room (chosen per multiplatform needs) behind repositories for durable offline-first behavior; don't treat HTTP cache as a database.
- Do not cache personalized responses publicly. Configure private cache policy and server headers once the API's auth/cache rules are known.

## Errors and UI state

`ApiResult` separates success from normalized HTTP, serialization, network, and unknown failures. Translate technical errors into user-facing messages at the feature boundary; don't show raw server exception text. Cancellation should propagate, not become an error screen.

`UiState` represents loading, content, and recoverable error. Use `LoadingContent`, `ErrorContent`, and `EmptyContent` for consistent common UI. Feature-specific content remains in its screen composable.

## Dependency injection and lifecycle

`core/di/AppModules.kt` is the Koin root. `App()` starts Koin for the shared Compose tree. Register singletons for clients/repositories and ViewModels in feature modules as screens are added. Keep platform-only dependencies in platform source sets and expose common abstractions where shared code needs them.

Use lifecycle ViewModels from common code and collect flows lifecycle-aware in Compose when screen flows are implemented. Avoid putting network calls directly in composables or constructing repositories in screens.

## Utilities and conventions

Put cross-feature helpers in small, focused `core` packages (network, UI, formatting, validation). Keep pure Kotlin utilities platform-independent. Avoid a generic catch-all utils file. Add shared tests for pure mapping/validation logic and repository behavior when feature requirements are known.

## Suggested feature layout

```
feature/<name>/
  data/       DTOs, API service, repository implementation, mappers
  domain/     models, repository interface, use cases only when useful
  presentation/ ViewModel, UiState/events, screen and reusable feature UI
core/
  di/ network/ ui/ util/
```

Start with a repository and ViewModel per feature. Add use cases when a business operation has meaningful reuse or orchestration; avoid layers that only forward a call.
