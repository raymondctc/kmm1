//
//  NavTagListViewModel.swift
//  iosApp
//
//  Created by raymond on 26/7/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import NineGagKmm

class NavTagListViewModel: ObservableObject {
    @Published var tagList: [String] = []
    private var tagList1: [String] = []
    
    private let useCaseHelper = UseCaseHelper()
    
    func loadNavTagList() {
        useCaseHelper.fetchNavTagListUseCase.invoke(parameters: FetchNavTagListUseCase.NavTagListParam()).collect(
            collector: Collector<Result<TagListModel>> { v in
                v.getOrNull()?.tagList.keys.forEach({ key in
                    let map = v.getOrNull()!.tagList
                    map[key]!.forEach({ tagItem in
                        DispatchQueue.main.async {
                            self.tagList.append(", listKey=" + key + ", tagItem=" + tagItem.key)
                        }
                        
                    })
                })
            },
            completionHandler: { error in
//                print("___" + String(result.hashValue))
//                self.tagList.removeAll()
//                self.tagList.append(contentsOf: self.tagList1)
            }
        )
    }
}
