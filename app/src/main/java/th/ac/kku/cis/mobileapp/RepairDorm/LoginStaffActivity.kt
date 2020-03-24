package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginstaff.*

class LoginStaffActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "LoginAsStaff Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginstaff)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth!!.currentUser != null){
            startActivity(Intent(this, StaffActivity::class.java))
            finish()
        }

        loginstaff_btn.setOnClickListener {
            val email = loginstaff_email.text.toString().trim {it <= ' ' }
            val password = loginstaff_password.text.toString().trim {it <= ' ' }

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address.",Toast.LENGTH_LONG).show()
                Log.d(TAG,"Email was empty!")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password.",Toast.LENGTH_LONG).show()
                Log.d(TAG,"Password was empty!")
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener { task  ->
                if (!task.isSuccessful){
                    if (password.length < 6) {
                        loginstaff_password.error = "Please check your password. Password must have minimum 6 characters."
                        Log.d(TAG, "Enter your password less than 6 characters.")
                    }else {
                        Toast.makeText(this, "อีเมล์หรือรหัสผ่านไม่ถูกต้อง" ,Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Sign in successfully!" ,Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this, StaffActivity::class.java))
                    finish()
                }
            }
        }
    }
}
