package ptu.edu.retrofitdemo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class DesktopActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHello.text="您好"
       tvHello.setOnClickListener {
           startActivity(Intent(it.context,MainActivity::class.java))
       }
    }
}
