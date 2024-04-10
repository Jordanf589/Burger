package com.example.firebaserestaurantapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.database
var arrCustomers = ArrayList<Customer>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "firebase";
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnAdd : Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val customer : TextView = findViewById(R.id.txtCustomerName)
            val faveItem : TextView = findViewById(R.id.txtFaveItem)

            // Write a message to the database
            val database = Firebase.database
            val myRef1 = database.getReference(customer.text.toString()) //The link to the database (the key)

            myRef1.setValue(faveItem.text.toString())
           // val myRef2 = database.getReference("FaveItem") //The link to the database (the key)

            //myRef2.setValue())
            database.getReference()
            arrCustomers.add(Customer(customer.toString(), faveItem.toString()))

            val feed : RecyclerView = findViewById(R.id.rvList)
            var custAdapter : CustomerAdapter
            custAdapter = CustomerAdapter()

            feed.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = custAdapter
            }
        }
    }
}