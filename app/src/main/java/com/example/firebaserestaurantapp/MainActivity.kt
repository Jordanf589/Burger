package com.example.firebaserestaurantapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

var arrCustomers = ArrayList<String>()


class MainActivity : AppCompatActivity() {
    private lateinit var rootNode:FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var listView: ListView
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
        listView = findViewById(R.id.lvCustomers)
        rootNode = FirebaseDatabase.getInstance()
        userReference = rootNode.getReference("customer")
        val database = Firebase.database
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val customer: TextView = findViewById(R.id.txtCustomerName)
            val faveItem: TextView = findViewById(R.id.txtFaveItem)
            val cust = Customer(customer.text.toString(), faveItem.text.toString())
            // Write a message to the database
            userReference.child(customer.text.toString()).setValue(cust)
        }
        val list = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.users, list)
        listView.adapter = adapter
        userReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(snapshot1 in snapshot.children){
                    val dc2 = snapshot1.getValue(Customer:: class.java)
                    val txt = "${dc2?.Name} - ${dc2?.Item}"
                    txt?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })


    }
}