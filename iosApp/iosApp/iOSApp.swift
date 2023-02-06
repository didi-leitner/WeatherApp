import SwiftUI
import shared


@main
struct iOSApp: App {
 

    let repo = WeatherRepo(sdk: WeatherAppSDK(databaseDriverFactory: DatabaseDriverFactory()))

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(repo: repo))

        }
    }
}
