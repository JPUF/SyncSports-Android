package com.jlbennett.syncsports

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The underlying Activity class.
 *
 * This activity hosts all other fragments, which are responsible for displaying each individual screen. As can be seen in activity_main.xml, this MainActivity simply has a singular 'NavHostFragment' which controls the currently visible Fragment, via the Navigation component.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
