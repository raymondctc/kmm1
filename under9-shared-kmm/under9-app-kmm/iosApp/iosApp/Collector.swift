//
//  Collector.swift
//  iosApp
//
//  Created by raymond on 26/7/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import NineGagKmm

class Collector<T> : Kotlinx_coroutines_coreFlowCollector {
    
  let callback:(T) -> Void

  init(callback: @escaping (T) -> Void) {
    self.callback = callback
  }

  func emit(value: Any?) async throws {
    if let v = value as? T {
      callback(v)
    }
  }
}
