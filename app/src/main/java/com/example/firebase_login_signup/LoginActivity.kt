
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
import com.example.firebase_login_signup.databinding.ActicityLoginBinding
import com.example.firebase_login_signup.databinding.ActivitySpBinding
import com.example.firebase_login_signup.ui.theme.Firebase_login_SignupTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.res.Resources // (for accessing resources)

class LoginActivity : AppCompatActivity() {
    private val binding : ActicityLoginBinding by lazy {
        ActicityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var Auth: FirebaseAuth

//    override fun onStart(){ // made by me
//        // when the app starts we get this
//        super.onStart()
//        // check if user already logged in
//        val currentUser : FirebaseUser? = Auth.currentUser
//        if(currentUser!=null){
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()


        Auth=FirebaseAuth.getInstance()

        binding.logintBtn.setOnClickListener {
            val userName = binding.emailBox.text.toString()
            val passWord = binding.passwordBox.text.toString()
            if(userName.isEmpty() || passWord.isEmpty()){
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                Auth.signInWithEmailAndPassword(userName , passWord)
                    .addOnCompleteListener{
                        task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "SignIn failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            
                        }
                    }
            }
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }


    }
}
