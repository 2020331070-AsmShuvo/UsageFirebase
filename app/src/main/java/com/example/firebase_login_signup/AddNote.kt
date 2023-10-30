package com.example.firebase_login_signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebase_login_signup.databinding.ActivityAddNoteBinding
import com.example.firebase_login_signup.ui.theme.Firebase_login_SignupTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNote : AppCompatActivity() {

    private val binding : ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private lateinit var databaseRef : DatabaseReference
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //intializing firebase database
        databaseRef = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()


        binding.saveNoteBtn.setOnClickListener {
            //get text from edit text
            val title = binding.etTitle.text.toString()
            val desc = binding.etDescription.text.toString()
            if(title.isEmpty() || desc.isEmpty()){
                Toast.makeText(this,"Fill all the details", Toast.LENGTH_LONG).show()
            }
            else{
                val curUser = auth.currentUser
                curUser?.let {
                    user->
                        // generate a unique key for the note
                    val notekey: String? = databaseRef.child("users").child(user.uid).child("notes").push().key
                        //note item instance
                    val noteItem = NoteItem(title, desc)
                    if(notekey != null){
//                        addnotes to the user note
                        databaseRef.child("users").child(user.uid).child("notes").child(notekey).setValue(noteItem)
                            .addOnCompleteListener {
                                task->
                                    if(task.isSuccessful){
                                        Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this, "failed to save note", Toast.LENGTH_LONG).show()
                                    }
                            }
                    }
                }
            }

        }

    }
}
