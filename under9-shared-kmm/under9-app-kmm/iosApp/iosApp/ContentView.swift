import SwiftUI
import NineGagKmm

struct ContentView: View {
    @ObservedObject var viewModel = AuthenticationViewModel()
    @Binding var loginName: String
    @Binding var password: String
    
	var body: some View {
        ScrollView {
            VStack {
                TextField("username/email", text: $loginName)
                    .padding()
                    .border(Color(UIColor.separator))
                    .padding(.bottom).padding(.top).padding(.horizontal)
                    .autocapitalization(.none)
                SecureField("password", text: $password)
                    .padding()
                    .border(Color(UIColor.separator))
                    .padding(.horizontal)
                Spacer()
                Button("Login as guest", action: {
                    viewModel.loginGuest()
                })
                Button("Login with username/email", action: {
                    viewModel.loginWithLoginName(loginName: loginName, password: password)
                })
                Text("Current user=" + viewModel.username)
                    .padding(.top)
                    .font(.footnote)
                Text("Current hash=" + viewModel.authHash)
                    .padding(.horizontal)
                    .font(.footnote)
            }
        }
//        NavTagListView()
	}
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView()
//	}
//}
