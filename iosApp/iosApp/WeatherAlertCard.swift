import SwiftUI
import shared

struct WeatherAlertCard: View {
    var wAlert: WeatherAlert

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Event: \(wAlert.event)")
                Text("Effective: \(String(wAlert.startDateUTC))")
                Text("Sender: \(wAlert.sender)")
            }
            Spacer()
        }
    }
}


