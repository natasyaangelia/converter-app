## Architecture

The architecture of the application is based, apply and strictly complies with each of the following 5 points:
-   A single-activity architecture, using the Navigation component to manage fragment operations.
-   Android architecture components, part of Android Jetpack for give to project a robust design, testable and maintainable.
-   Pattern [Model-View-ViewModel (MVVM) facilitating a separation of development of the graphical user interface.
-   S.O.L.I.D design principles intended to make software designs more understandable, flexible and maintainable.
-   Modular app architecture allows to be developed features in isolation, independently from other features.

## Modules
The above graph shows the app modularisation:
-   `:app` depends on `:core` and indirectly depends on `:features` by dynamic-features.
-   `:features` modules depends on `:commons`, `:core`, `:libraries` and `:app`.
-   `:core` and `:commons` only depends for possible utils on `:libraries`.
-   `:libraries` don’t have any dependency.

## Source Data
The APK using the data from https://currency.getgeoapi.com/
because https://exchangeratesapi.io/ really strict with request and also my free key has been expired within a week. (so I looked to another exchange website API)

## Dependencies
-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [Benchmark](https://developer.android.com/studio/profile/benchmark.html) - handles warmup, measures your code performance, and outputs benchmarking results to the Android Studio console.
    -   [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - allows bind UI components in  layouts to data sources in  app using a declarative format rather than programmatically.
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Navigation](https://developer.android.com/guide/navigation/) - helps implement naviagtion to help create a simple to more complex flow patterns.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - managing background threads with simplified code and reducing needs for callbacks.
-   [Dagger2](https://dagger.dev/) - dependency injector for replacement all FactoryFactory classes.
-   [Retrofit](https://square.github.io/retrofit/) - type-safe HTTP client.
-   [Moshi](https://github.com/square/moshi) - makes it easy to parse JSON into Kotlin objects.
-   [Timber](https://github.com/JakeWharton/timber) - a logger with a small, extensible API which provides utility on top of Android's normal Log class.
-   [Stetho](http://facebook.github.io/stetho/) - debug bridge for applications via Chrome Developer Tools.

## Test dependencies

-   [Espresso](https://developer.android.com/training/testing/espresso) - to write concise, beautiful, and reliable Android UI tests
-   [JUnit](https://github.com/junit-team/junit4) - a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
-   [Mockk](https://github.com/mockk/mockk) - provides DSL to mock behavior. Built from zero to fit Kotlin language.
-   [AndroidX](https://github.com/android/android-test) - the androidx test library provides an extensive framework for testing Android apps.


## Further Improvements

-   I would like to add Robolectric and UIAutomator to test the UI are suitable for cross-app functional UI testing across system and installed apps.
-   Also because I don't have enough time to do more research, but I would love to improve the dependency injector using KOIN rather than Dagger on modularization project type. 
    I'm familiar and using KOIN a lot on my projects and at work, but all of them are monolithic app. Using KOIN would robust the codeline and make it simpler.
-   I think if the API is able for socket connection, I would be thrill to use websocket and put more real-time features on the app.
-   If I have more spare time I would like to secure the base_url and the key on NDK class, where it should be made the private variable more secure.

## APK Link

https://drive.google.com/file/d/1DkfcEa3azfv02ZFgPND0BDI7v7Giu56n/view?usp=sharing
