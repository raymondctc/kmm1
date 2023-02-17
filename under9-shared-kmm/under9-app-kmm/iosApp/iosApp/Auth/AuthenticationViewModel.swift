//
//  AuthenticationViewModel.swift
//  iosApp
//
//  Created by raymond on 22/7/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import NineGagKmm

class AuthenticationViewModel: ObservableObject {
    @Published var username: String = ""
    @Published var authHash: String = ""
    
    private let useCaseHelper = UseCaseHelper()
    
    func loginGuest() {
        useCaseHelper.authUseCase.invoke(
            parameters: AuthUseCase.Guest(),
            completionHandler: { result, error in
                DispatchQueue.main.async {
                    print("@@@ result=" + (result?.getOrNull()?.appToken?.userToken ?? ""))
                    self.username = "guest"
                    self.authHash = (result?.getOrNull()?.appToken?.userToken ?? "")
                }
            }
        )
    }
    
    func loginWithLoginName(loginName: String, password: String) {
        useCaseHelper.authUseCase.invoke(
            parameters: AuthUseCase.LoginName(loginName: loginName, password: password),
            completionHandler: { result, error in
                DispatchQueue.main.async {
                    self.username = (result?.getOrNull()?.user?.loginName ?? "")
                    self.authHash = (result?.getOrNull()?.appToken?.userToken ?? "")

                    print("@@@ result=" + (result?.getOrNull()?.appToken?.userToken ?? ""))

                    // For double write, such that API can be called in KMM wiht user token header
//                    self.useCaseHelper.cacheIosUserToken(userToken: self.authHash)
                }
            }
        )
    }
}
