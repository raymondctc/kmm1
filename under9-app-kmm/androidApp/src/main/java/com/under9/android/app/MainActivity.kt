package com.under9.android.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ninegag.app.shared.di.module.UseCaseHelper
import com.ninegag.app.shared.domain.tag.FetchNavTagListUseCase
import com.under9.shared.core.util.PlatformUtils
import io.github.aakira.napier.Napier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }

    fun greet(): String {
        val useCaseHelper = UseCaseHelper()
        GlobalScope.launch {
            useCaseHelper.fetchNavTagListUseCase(FetchNavTagListUseCase.NavTagListParam)
                .collect {
                    Napier.d("it=$it")
                }
        }

        return "Hello, ${PlatformUtils.platform}"
    }
}
