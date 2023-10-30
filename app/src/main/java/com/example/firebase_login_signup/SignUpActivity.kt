package com.example.firebase_login_signup

import android.content.Intent
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
import com.example.firebase_login_signup.databinding.ActivitySignupBinding
import com.example.firebase_login_signup.ui.theme.Firebase_login_SignupTheme
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
            // intialize auth firebase
        auth = FirebaseAuth.getInstance()

        binding.LoginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.registerBtn.setOnClickListener {
            // get text from edit text fields:
            val email = binding.giveEmail.text.toString()
            val username = binding.giveUsername.text.toString()
            val password = binding.givePassword.text.toString()
            val retypePassword = binding.giveRetypePassword.text.toString()
            //check if any field is blank
            if(email.isEmpty() || password.isEmpty() || username.isEmpty() || retypePassword.isEmpty()){
                Toast.makeText(this, "Please Fill all the items", Toast.LENGTH_SHORT).show()
            }
            else if(password != retypePassword){
                Toast.makeText(this, "Retype password Must be same", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
                            // after reg completed, goto login page
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Registration Failled: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }
}