//
//  NavTagListView.swift
//  iosApp
//
//  Created by raymond on 26/7/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct NavTagListView: View {
    @ObservedObject var viewModel = NavTagListViewModel()
    
    var body: some View {
        ScrollView {
            ForEach(viewModel.tagList, id: \.self) { tagItem in
                Text("tagItem=" + tagItem)
            }
            Button("Load list", action: {
                viewModel.loadNavTagList()
            })
        }
    }
}
