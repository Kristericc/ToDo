package com.kristers.todo.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kristers.todo.MainActivity
import com.kristers.todo.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        observeUser()
        observeError()

        viewModel.getLoggedUser()

        binding.loginBtn.setOnClickListener {
            if (binding.email.text!!.isNotEmpty() && binding.password.text!!.isNotEmpty()) {
                if (binding.password.text!!.length >= 6) {
                    if (isEmailValid("" + binding.email.text)) {
                        viewModel.clearTodoTable()
                        viewModel.logInUser("" + binding.email.text, "" + binding.password.text)
                    } else {
                        Toast.makeText(this, "Your credentials are not valid", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Your password is too short", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Your credentials are not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeUser() {

        viewModel.user.observe(
            this,
            {
                if (it != null) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            },
        )
    }
    private fun observeError() {
        viewModel.error.observe(
            this,
            {
                Toast.makeText(this, "Some one is already using this email address, or your password was not correct", Toast.LENGTH_SHORT).show()
            },
        )
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
