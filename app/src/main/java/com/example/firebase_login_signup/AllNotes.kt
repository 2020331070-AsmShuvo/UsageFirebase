package com.example.firebase_login_signup

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_login_signup.databinding.ActivityAllNotesBinding
import com.example.firebase_login_signup.ui.theme.Firebase_login_SignupTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNotes : AppCompatActivity() {
    private val binding : ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }
    private lateinit var databaseRef : DatabaseReference
    private lateinit var auth : FirebaseAuth
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView  = binding.noteRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        // initialized
            databaseRef = FirebaseDatabase.getInstance().reference
            auth = FirebaseAuth.getInstance()

            val curUser = auth.currentUser
            curUser?.let {
                user->
                val noteRef = databaseRef.child("users").child(user.uid).child("notes")
                noteRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val noteList = mutableListOf<NoteItem>()
                        for(noteSnapshot in snapshot.children){
                            val note = noteSnapshot.getValue(NoteItem::class.java)
                            note?.let {
                                noteList.add(it)
                            }
                        }
                        noteList.reverse()
                        val adapter = noteAdapter(noteList)
                        recyclerView.adapter = adapter

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            }



    }
}
