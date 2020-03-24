package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.TextView

import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loginstudent.*
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoStudent
import th.ac.kku.cis.mobileapp.RepairDorm.R
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
//import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//import com.google.firebase.ktx.Firebase


class LoginStudentActivity : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    private var StudentID: String = ""
    private var Password: String = ""
    private val TAG: String = "LoginStudentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginstudent)


        loginstudent_btn.setOnClickListener {
            StudentID = loginstudent_stdid.text.toString()
            Password = loginstudent_password.text.toString()

            if (StudentID.trim(' ') == "") {
                Toast.makeText(this@LoginStudentActivity, "กรุณากรอกรหัสนักศึกษา", Toast.LENGTH_LONG).show()
            }

            else if (StudentID.length != 11) {
                Toast.makeText(this@LoginStudentActivity, "รหัสนักศึกษาไม่ถูกต้อง", Toast.LENGTH_LONG).show()

            }
            else if (Password.trim(' ') == "") {
                Toast.makeText(this@LoginStudentActivity, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_LONG).show()

            }
            else if (Password.length != 13) {
                Toast.makeText(this@LoginStudentActivity, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_LONG).show()
            }

            checkIDPassword(StudentID, Password)
        }
    }


    fun checkIDPassword(id: String, pw: String) {

        FirebaseDatabase.getInstance().reference.child("students")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {


                    if (p0.hasChild(id)) {
                        Log.d(TAG, "ค้นพบ " + StudentID + " ในระบบ")
                        Log.d(TAG, "ค้นพบ " + p0.child(id).child("numID").getValue().toString() + " ในระบบ")
                            //Toast.makeText(this@LoginStudentActivity,"OK",Toast.LENGTH_SHORT).show()
                        if (p0.child(id).child("numID").getValue().toString() == pw) {
                            Log.d(TAG, "รหัสผ่านถูก")
                            Log.d(TAG, Firebase.database.reference.child("students").child(StudentID).toString())

                            val infoStudent = object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {

                                    val info = dataSnapshot.getValue<infoStudent>()

                                    if (info != null) {
                                        Toast.makeText(this@LoginStudentActivity,
                                            "สวัสดี คุณ " + info.Firstname + " " + info.Lastname,Toast.LENGTH_SHORT).show()

                                        val d = Intent(this@LoginStudentActivity, StudentActivity2::class.java)
                                        d.putExtra("stdid", StudentID)
                                        //d.putExtra("email" , info.Email)
                                        d.putExtra("firstname", info.Firstname)
                                        d.putExtra("lastname", info.Lastname)
                                        d.putExtra("numid", info.numID)
                                        d.putExtra("roomid", info.roomNo)
                                        startActivity(d)
                                    }
                                }

                                override fun onCancelled(p0: DatabaseError) {
                                    TODO("not implemented")
                                }

                            }

                            Log.d(TAG,"Before Use FB"+ Firebase.database.reference.child("students").child(StudentID).toString())
                            Firebase.database.reference.child("students").child(StudentID).addValueEventListener(infoStudent)
                        }



                    } else {
                        Toast.makeText(this@LoginStudentActivity, "ไม่พบรหัสนักศึกษา " + StudentID + " ในระบบ", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "ไม่พบรหัสนักศึกษา " + StudentID + " ในระบบ")
                    }


                }
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented")
                }
            })

    }
}