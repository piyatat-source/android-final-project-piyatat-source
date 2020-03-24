package th.ac.kku.cis.mobileapp.RepairDorm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_test.*

class Test : AppCompatActivity() {

    var txtid :String? = ""
    var txtpw :String? = ""
    var TAG = "TestActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        btn_check.setOnClickListener{
            txtid = get_stdid.text.toString()
            txtpw = get_stdid2.text.toString()
            Log.d(TAG, "รับรหัสมาแล้ว")

            val CallFB = object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    if  (p0.hasChild(txtid.toString()))  {
                        Log.d(TAG, "พบ " + txtid)
                    } else {
                        Log.d(TAG, "ไม่พบ " + txtid)
                    }
                }


                override fun onCancelled(p0: DatabaseError) {

                }

            }
            Log.d(TAG, "สร้างหน้าที่แล้ว")
            Firebase.database.reference.child("students").addValueEventListener(CallFB)
            Log.d(TAG, Firebase.database.reference.child("students").toString())
        }
    }
}
