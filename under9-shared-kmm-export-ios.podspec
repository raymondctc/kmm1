Pod::Spec.new do |s|
  s.name             = 'under9-shared-kmm-export-ios'
  s.version          = '1.0.0'
  s.summary          = '9GAG KMM iOS framework'
  s.homepage         = 'https://github.com/9gag/under9-shared-kmm-export-ios'
  s.license          = { :type => 'Custom', :file => 'LICENSE' }
  s.author           = { 'ios' => 'ios@9gag.com' }
  s.source           = { :git => 'https://github.com/9gag/under9-shared-kmm-export-ios.git', :tag => s.version.to_s }

  s.swift_version = '5.5'
  s.ios.deployment_target = '11.0'
  s.framework = "WebKit"

  s.default_subspec = "Release"

  s.subspec "Debug" do |ss|
    ss.vendored_frameworks = 'ninegag-shared-lib/build/XCFrameworks/debug/NineGagKmm.xcframework'
    ss.resources           = 'ninegag-shared-lib/build/XCFrameworks/debug/NineGagKmm.xcframework'
  end

  s.subspec 'Release' do |ss|
    ss.vendored_frameworks = 'ninegag-shared-lib/build/XCFrameworks/release/NineGagKmm.xcframework'
    ss.resources           = 'ninegag-shared-lib/build/XCFrameworks/release/NineGagKmm.xcframework'
  end

end
