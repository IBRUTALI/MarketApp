package com.ighorosipov.marketapp.utils.base

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.ighorosipov.marketapp.R
import java.util.regex.Pattern

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun MaterialToolbar.setTitle(label: CharSequence?, textView: TextView, arguments: Bundle?) {
    if (label != null) {
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments.get(argName).toString())
            } else {
                return
            }
        }
        matcher.appendTail(title)
        setTitle("")
        textView.text = title
    }
}

operator fun <T> MutableLiveData<List<T>>.plusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.postValue(value + listOf(item))
}

fun List<String>.remove(string: String): List<String> {
    val result = this.toMutableList()
    result.remove(string)
    return result
}