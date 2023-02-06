# WeatherApp Multiplatform

Since Kotlin Multiplatform was promoted to Beta recently (october 2022), i wanted to experiment with it, so i developed this project with KMP
  - Android app is working, meeting all the requirements.
  - iOS app is working, without pictures and styling (just displays the alerts).

Tech-stack: Jetpack Compose, Kotlin Coroutines, Ktor (networking), SQLDelight (db), Koin (di), Coil (image-loading)

(Clean) Architecture:
In the SHARED module, in commonsMain, there are the packages that would correspond to core modules (db, network, model etc), shared between the Android and the iOS app. 
For simplicity i left them as packages, not Android modules/libraries, but could be extracted to independent modules.

Dependency graph:
UI -> ViewModel -> Repository -> SDK --> DB
                                     --> Network 
*all of the above depend on Model

Patterns used:
#MVVM: View implemented with single-Activity and composable functions. (Swift on iOS) 
Most of the composables are stateless, except the screen-level ones where the state is collected from the ViewModel in a lifecycle-aware manner.
When there's a change in the observed state, the Composable will be recomposed.
The viewModel observes a repositry, and supplies the data/view-state to the view. Also exposes public function to the view that represent user events (click, pull)

#Repository pattern: Used a repository as an abstraction for the underlying data-sources (db, network). Gets and combines data from datasources. 
Also used an interface here, so the real repository can be swapped with a fake one when unit-testing the viewModels.

#Dependency injection: for its many benefits..

*to run the Android App, the minimum Android Studio version is Dolphin (AGP 7.3 compatibility)
*to run the iOSApp, OS X with XCode needed

TESTED on
OS X + Android Studio Flamingo
OS X + Android Studio Electric Eel
Windows + Android Studio Flaming

Devices: Pixel5/Api33; Emulators: Pixel6/Api30; iPhone14/iOS16.2





