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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

var arrCustomers = ArrayList<String>()


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

        val database = Firebase.database
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val customer: TextView = findViewById(R.id.txtCustomerName)
            val faveItem: TextView = findViewById(R.id.txtFaveItem)

            // Write a message to the database

            val myRef =
                database.getReference(customer.text.toString()) //The link to the database (the key)

            myRef.setValue(faveItem.text.toString())
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue<String>()
                    val key = snapshot.key
                    val cust = key.toString() + "-" + value.toString()
                    val item = cust
                    Log.d(TAG, "Value is: " + value)
                    arrCustomers.add(item.toString());
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }

            })
            val listView = findViewById<ListView>(R.id.lvCustomers)
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrCustomers)
            listView.adapter = adapter
            listView.onItemClickListener = object : AdapterView.OnItemClickListener{
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@MainActivity, arrCustomers[position].toString(), Toast.LENGTH_SHORT).show()
                    val temp = database.getReference(arrCustomers[position].toString().substringBefore("-"))
                    temp.removeValue()
                    arrCustomers.remove(position.toString())
                }

            }
        }

        val remove :Button = findViewById(R.id.btnRemove)
        remove.setOnClickListener{
            val listView = findViewById<ListView>(R.id.lvCustomers)
            val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrCustomers)
            listView.adapter = adapter

        }

    }
}