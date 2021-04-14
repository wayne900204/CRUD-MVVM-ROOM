package com.example.kotlinroom

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroom.viewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_AddUser.setOnClickListener(onButtonListener)
        // init viewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val myAdapter = MyAdapter(userViewModel,this)
        recyclerView.apply {
            recyclerView.adapter = myAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // observe data changes in db
        userViewModel.getAllItems.observe(this, Observer { items ->
            items?.let {
                myAdapter.updateScreen(it)
                myAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)

        val firstName = dialog.findViewById<EditText>(R.id.firstName)
        val lastName = dialog.findViewById<EditText>(R.id.lastName)
        val add = dialog.findViewById<Button>(R.id.add)

        add.setOnClickListener {
            if(firstName.text.isNotEmpty()&&lastName.text.isNotEmpty()){
                GlobalScope.launch {
                    userViewModel.insert(
                        firstName.text.toString(),
                        lastName.text.toString()
                    )
                }
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Can Not Be Null",Toast.LENGTH_SHORT).show()
            }

        }
        dialog.show()
    }

    private val onButtonListener = View.OnClickListener {
        when (it.id) {
            R.id.btn_AddUser -> {
                showDialog()
            }
        }
    }
}
