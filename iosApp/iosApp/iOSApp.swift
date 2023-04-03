import SwiftUI
import shared


@main
struct iOSApp: App {
 

    let repo = WeatherRepo(databaseDriverFactory: DatabaseDriverFactory())

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(repo: repo))

        }
    }
}
