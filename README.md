# WeatherForecast
Sample Weather Forecast Android Application

## Table of Contents

- [App](#app)
- [Architecture](#architecture)
- [Project Setup](#project-setup)
- [ScreenShots](#screenshots)
- [Libraries](#libraries)

## App
A demo application showing weather forecast of your current location.
The project has been written in Kotlin language. For network requests, it uses Retrofit with RxJava and Moshi and Room for local caching.
Dagger2 has been used for Dependency injection.

## Architecture
The project is built using the Clean architectural pattern. Clean architecture allows for the separation of concern which also makes testing easier.

## Project Setup
To configure the project get the api key from [Openweathermap](https://openweathermap.org). Add this to your local.properties file in the project
```
API_KEY=ADD_YOUR_API_KEY_HERE

```

## ScreenShots
   <img src="art/Screenshot_1.png" width="200" style="max-width:100%;">  <img src="art/Screenshot_2.png" width="200" style="max-width:100%;">  <img src="art/Screenshot_3.png" width="200" style="max-width:100%;"> 
   <img src="art/Screenshot_4.png" width="200" style="max-width:100%;">  <img src="art/Screenshot_5.png" width="200" style="max-width:100%;">  <img src="art/Screenshot_6.png" width="200" style="max-width:100%;"> 

## Libraries

Libraries used in the whole application are:

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
- [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [RxJava](https://github.com/ReactiveX/RxJava) - RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.
- [Dagger2](https://dagger.dev/dev-guide/) - Used for Dependency injection
- [Retrofit](https://square.github.io/retrofit/) - Turns your HTTP API into a Java interface.
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) - Enables mock creation, verification and stubbing for testing
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients
- [Glide](https://github.com/bumptech/glide) - Media management and image loading framework for Android