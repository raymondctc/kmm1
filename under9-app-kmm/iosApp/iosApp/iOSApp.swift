import SwiftUI
import NineGagKmm

@main
struct AppDelegate: App {
    @State var loginName: String = ""
    @State var password: String = ""
    
	var body: some Scene {
		WindowGroup {
			ContentView(loginName: $loginName, password: $password)
		}
	}
    
    init() {
        let userAgentConfig = UserAgentConfig(
            packageName: "com.9gag.ios.stg",
            packageVersion: "123456",
            deviceUUIDProvider: { return "v123-223" },
            userAgentProvider: { return "9GAG KMM" }
        )
        
        KmmSdkKt.doInitSdk(
            userDefaults: UserDefaults.standard,
            userAgentConfig: userAgentConfig,
            doOnStartup: {
                print("logged, appId=" + KmmSdkKt.appConfig.appId)
            },
            env: Env.staging
        )
    }
}
