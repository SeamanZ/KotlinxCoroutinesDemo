package com.example.kotlinxcoroutinesdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay

//https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setup(hello, fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    private fun setup(
        hello: TextView,
        fab: FloatingActionButton
    ) {
//        //Launch UI coroutine
//        //launch coroutine in the main thread
//        val job = GlobalScope.launch(Dispatchers.Main) {
//            for (i in 10 downTo 1) { // countdown from 10 to 1
//                hello.text = "Countdown $i ..." // update text
//                delay(500) // wait half a second
//            }
//
//            hello.text = "Done!"
//        }
//
//        //Cancel UI coroutine
//        fab.setOnClickListener {
//            job.cancel() // cancel coroutine on click
//        }


        // start coroutine when the circle is clicked
        fab.onClick {
            for (i in 10 downTo 1) { // countdown from 10 to 1
                hello.text = "Countdown $i ..." // update text
                delay(500) // wait half a second
            }
            hello.text = "Done!"
        }
    }

    fun View.onClick(action: suspend () -> Unit) {

//        setOnClickListener {
//            GlobalScope.launch(Dispatchers.Main) {
//                action()
//            }
//        }

        // launch one actor
        val actor = GlobalScope.actor<View>(Dispatchers.Main) {
            for (event in channel) {
                action()
            }
        }

        // install a listener to activate this actor
        setOnClickListener {
            actor.offer(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
