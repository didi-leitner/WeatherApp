# WeatherApp Multiplatform

Since Kotlin Multiplatform was promoted to Beta recently (october 2022), i wanted to experiment with it, so i developed this project with KMP.
  - Android app is working, meeting all the requirements.
  - iOS app is working, without pictures and styling (just displays the alerts).

Tech-stack: Jetpack Compose, Kotlin Coroutines, Ktor, SQLDelight, Koin, Coil

Architecture:
Used the clean architecture approach.
In the SHARED module, in commonsMain, there are the packages that would correspond to core modules (db, network, model etc), shared between the Android and the iOS app. 
For simplicity i left them as packages, not Android modules/libraries.
Lower-level layers




