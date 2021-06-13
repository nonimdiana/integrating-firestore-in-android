package com.firestore.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firestore.intro.databinding.ActivityMainBinding

const val TAG = "FIRESTORE"

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        handleClicks()
    }

    private fun handleClicks() {
        binding!!.btnUploadData.setOnClickListener {
            val hashMap = hashMapOf<String, Any>(
                "name" to "John doe",
                "city" to "Nairobi",
                "age" to 24
            )

            FirebaseUtils().fireStoreDatabase.collection("users")
                .add(hashMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Added document with ID ${it.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding document $exception")
                }
        }

        binding!!.btnReadData.setOnClickListener {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { document ->
                        Log.d(TAG, "Read document with ID ${document.id}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents $exception")
                }
        }
    }
}