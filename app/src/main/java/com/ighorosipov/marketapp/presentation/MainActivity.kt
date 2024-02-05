package com.ighorosipov.marketapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ActivityMainBinding
import com.ighorosipov.marketapp.presentation.tabs.TabsFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel
import java.util.regex.Pattern

class MainActivity: AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private var navController: NavController? = null
    private val topLevelDestinations = setOf(getTabsDestination(), getSignInDestination())

    private val viewModel: MainActivityViewModel by lazyViewModel {
        appComponent().mainActivityViewModel().create()
    }

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
        binding.toolbar.setTitle(destination.label, binding.toolbarTextView, arguments)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inject()
        val navController = getRootNavController()
        subscribeToObservers(navController)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
        binding.toolbar.apply {
            setSupportActionBar(this)
        }


    }

    fun inject() {
        appComponent().inject(this)
    }

    private fun subscribeToObservers(navController: NavController) {
        viewModel.isSignIn.observe(this) { result ->
            prepareRootNavController(result, navController)
            onNavControllerActivated(navController)
        }
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    private fun getRootNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        return navHost.navController
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun prepareRootNavController(isSignedIn: Boolean, navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(
            if (isSignedIn) {
                getTabsDestination()
            } else {
                getSignInDestination()
            }
        )
        navController.graph = graph
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.nav_graph

    private fun getTabsDestination(): Int = R.id.tabsFragment

    private fun getSignInDestination(): Int = R.id.loginFragment

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
        mBinding = null
    }

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