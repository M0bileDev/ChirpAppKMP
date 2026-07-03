# ChirpAppKMP

A **Kotlin Multiplatform** chat application that shares its entire UI and business logic across **Android** and **iOS** using **Compose Multiplatform**.

The app supports account registration and email verification, login and password reset, a chat list with an adaptive list/detail layout, one-to-one and group chats, chat management, and a user profile with avatar media picking.

> **Targets:** Android (`androidMain`) and iOS (`iosArm64`, `iosSimulatorArm64`).
> The convention plugins do **not** currently configure a Desktop/JVM target.

## Tech Stack

| Concern | Library |
| --- | --- |
| UI | Compose Multiplatform, Material 3, Compose Navigation, Adaptive layouts |
| Dependency injection | Koin |
| Networking | Ktor client (OkHttp on Android, Darwin on iOS) + WebSockets |
| Local persistence | Room (KMP) + SQLite, DataStore |
| Serialization | kotlinx.serialization |
| Async | kotlinx.coroutines |
| Images | Coil 3 |
| Logging | Kermit |
| Build config | BuildKonfig |
| Firebase | Firebase BOM (Android) |

Versions are centralized in the version catalog at [`gradle/libs.versions.toml`](./gradle/libs.versions.toml).

## Architecture

The project is split into many small Gradle modules following a strict **layered, per-feature** structure. Module dependencies only point **downward**:

```
presentation → domain ← data → database
                  ↑
             core/* (shared by everything)
```

### Modules

- **`composeApp`** — shared application code and entry point (`App.kt`, `NavigationRoot.kt`). Aggregates every feature/core module and starts Koin via `initKoin()`.
- **`androidApp`** — the Android application module (Android manifest, launcher, Firebase / Google Services). Depends on `composeApp`.
- **`iosApp`** — the iOS application entry point (open in Xcode). Consumes the shared Kotlin code as a framework.
- **`core/`**
  - `core/domain` — shared domain models and interfaces (no platform deps).
  - `core/data` — shared data implementations and platform bindings.
  - `core/presentation` — shared presentation utilities.
  - `core/designsystem` — reusable `Chirp*` Compose components.
- **`feature/auth/`** — `presentation`, `domain` (login, register, email verification, forgot/reset password).
- **`feature/chat/`** — `presentation`, `domain`, `data`, `database` (chat list, chat detail, create/manage chat, profile). `feature/chat/database` is the Room KMP database; exported schemas live in [`feature/chat/database/schemas/`](./feature/chat/database/schemas).

### Convention plugins (`build-logic/`)

Module build files are intentionally tiny because shared configuration lives in `build-logic/convention/` as plugins applied by id:

- `convention.kmp.library` — base KMP library (Android + iOS targets; namespace/framework name derived from the module path).
- `convention.cmp.library` — KMP library + Compose Multiplatform.
- `convention.cmp.feature` — `cmp.library` + Koin, Compose navigation/viewmodel, and `core:presentation`/`core:designsystem`. Use for `feature/*/presentation`.
- `convention.cmp.application` — the `composeApp` itself.
- `convention.android.application` — the `androidApp` module.
- `convention.room` — applies KSP + Room; registers `kspAndroid` / `kspIosSimulatorArm64` / `kspIosArm64` and sets the schema dir.
- `convention.buildkonfig` — generates `BuildKonfig` with `API_KEY` (package derived from the module path).

### Key patterns

- **MVI presentation** — each screen folder contains `XxxViewModel`, `XxxState`, `XxxAction`, and (when needed) `XxxEvent`.
- **Koin DI** — one module per layer (`coreDataModule`, `chatDataModule`, `chatPresentationModule`, `corePresentationModule`, `authPresentationModule`, …), all registered in `composeApp/.../di/initKoin.kt`. Platform bindings use `expect`/`actual` module declarations.
- **expect/actual** — common code in `Platform.kt` with `Platform.android.kt` / `Platform.ios.kt` actuals; the iOS data layer also uses a cinterop (`network.def`).
- **Offline-first repositories** — `OfflineFirst*Repository` read/write the Room cache and reconcile with Ktor/WebSocket services.

## Prerequisites

- JDK 17+
- Android Studio (latest) with the Kotlin Multiplatform tooling
- Xcode (for building/running the iOS app)
- **`API_KEY` in `local.properties`** — `BuildKonfigConventionPlugin` reads it and **fails the build if it is missing**:

  ```properties
  API_KEY=your_api_key_here
  ```

## Build & Run

### Android

```shell
./gradlew :composeApp:assembleDebug   # build the Android APK
```

Or use the run configuration from the run widget in the IDE toolbar.

### iOS

Open the [`iosApp/`](./iosApp) directory in Xcode and run, or use the IDE run configuration. The shared Kotlin code is exposed as a framework whose `baseName` is derived from the module path (e.g. `:core:domain` → `CoreDomain`).

### Everything

```shell
./gradlew build
```

### Validating Room DAOs on iOS

For source sets targeting non-Android platforms, Room requires every `@Query` method to be **either `suspend` or return an observable type (`Flow`)**. Always validate DAO changes with the iOS KSP task, not just the Android build:

```shell
./gradlew :feature:chat:database:kspKotlinIosSimulatorArm64
```

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) and [Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform.html).
