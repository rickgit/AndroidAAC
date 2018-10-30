package ptu.edu.retrofitdemo

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ptu.edu.retrofitdemo.viewModel.MainViewModel


class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)
        viewModel.queryData()


    }

}
