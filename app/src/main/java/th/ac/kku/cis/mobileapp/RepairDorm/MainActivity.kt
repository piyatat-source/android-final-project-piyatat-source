package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null


    private val TAG: String = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()


        if(mAuth!!.currentUser != null){
            Log.d(TAG, "Continue with : " + mAuth!!. currentUser!!.email)
            startActivity(Intent(this, StaffActivity::class.java))
            finish()
        }

        btn_asStaff.setOnClickListener {
            startActivity(Intent(this, LoginStaffActivity::class.java))
        }

        checkCurrentUser()
    }

    //check current user from database
    private fun checkCurrentUser() {
        val studentUser = FirebaseAuth.getInstance().currentUser
        if (studentUser != null) {
            startActivity(Intent(this, StudentActivity2::class.java))
            finish()
        }

        btn_asStudent.setOnClickListener {
            startActivity(Intent(this, LoginStudentActivity::class.java))
        }
    }
}
