import SwiftUI
import shared

struct ContentView: View {


    @ObservedObject private(set) var viewModel: ViewModel

         var body: some View {
             NavigationView {
                 listView()
                 .navigationBarItems(trailing:
                     Button("Reload") {
                         self.viewModel.getAllAlerts(forceReload: true)
                 })
             }
         }

         private func listView() -> AnyView {
             switch viewModel.alerts {
             case .loading:
                 return AnyView(Text("Loading...").multilineTextAlignment(.center))
             case .result(let alerts):
                 return AnyView(List(alerts) { alert in
                     WeatherAlertCard(wAlert: alert)
                 })
             case .error(let description):
                 return AnyView(Text(description).multilineTextAlignment(.center))
             }
         }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView()
//	}
//}

extension ContentView {
    enum LoadableLaunches {
        case loading
        case result([WeatherAlert])
        case error(String)
    }

    class ViewModel: ObservableObject {
        let repo: WeatherRepo
        @Published var alerts = LoadableLaunches.loading
        //private var pollShoppingListItemsTask: Task<(), Never>? = nil


        init(repo: WeatherRepo) {
            self.repo = repo
            self.getAllAlerts(forceReload: false)

            /*pollShoppingListItemsTask = Task {
              do {
                let stream = asyncStream(
                    for: sdk.getLaunchesFromDB()

                )
                for try await data in stream {
                   self.items = data
                }
              } catch {
                print("Failed with error: \(error)")
              }
            }*/
        }




        func getAllAlerts(forceReload: Bool)  {
            self.alerts = .loading


            repo.getAllAlertsIOS(completionHandler: { alerts, error in
                       if let alerts = alerts {
                           self.alerts = .result(
                            alerts.map{(WeatherAlertEntity)-> WeatherAlert in
                                return WeatherAlert(id: WeatherAlertEntity.id,
                                                    event: WeatherAlertEntity.event,
                                                    startDateUTC: WeatherAlertEntity.startDateUTC,
                                                    endDateUTC: WeatherAlertEntity.endDateUTC,
                                                    sender: WeatherAlertEntity.sender,
                                                    desc: WeatherAlertEntity.desc,
                                                    severity: WeatherAlertEntity.severity,
                                                    certainty: WeatherAlertEntity.certainty,
                                                    urgency: WeatherAlertEntity.urgency,
                                                    affectedZones:WeatherAlertEntity.affectedZones,
                                                    instruction: WeatherAlertEntity.instruction)
                               
                           })
                           
                           } else {
                               self.alerts = .error(error?.localizedDescription ?? "error")
                           }
                       })



            /*sdk.refreshLaunchesFromAPI(completionHandler: {launches, error in
             if let launches = launches {
             self.launches = .result(launches)
             } else {
             self.launches = .error(error?.localizedDescription ?? "error")
             }
             })

             }*/

        }

    }

}
extension WeatherAlert: Identifiable { }



