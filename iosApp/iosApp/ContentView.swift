import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()
    let sharedGreet = SharedStrings().exampleGreeting().localized()

	var body: some View {
		Text(greet)
        Text(sharedGreet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
