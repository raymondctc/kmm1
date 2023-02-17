package androidx.lifecycle

import com.under9.shared.core.coroutines.AppMainScope
import com.under9.shared.core.coroutines.PlatformDispatcherProvider

// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:lifecycle/lifecycle-viewmodel-ktx/src/main/java/androidx/lifecycle/ViewModel.kt;l=35?q=viewmodelscope&sq=&ss=androidx%2Fplatform%2Fframeworks%2Fsupport

private const val JOB_KEY = "androidx.lifecycle.AppViewModelCoroutineScope.JOB_KEY"

public val ViewModel.appMainScope: AppMainScope
    get() {
        val scope: AppMainScope? = this.getTag(JOB_KEY) as AppMainScope?
        if (scope != null) {
            appMainScope.viewModel = this
            return scope
        }
        val appMainScope = AppMainScope(PlatformDispatcherProvider())
        appMainScope.viewModel = this
        return setTagIfAbsent(
            JOB_KEY, appMainScope
        )
    }