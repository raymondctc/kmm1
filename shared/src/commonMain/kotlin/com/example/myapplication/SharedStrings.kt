package com.example.myapplication

import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import com.under9.exampleapp.shared.res.MR

object SharedStrings {
    fun exampleGreeting(): StringDesc = StringDesc.Resource(MR.strings.sample_greeting)
}