package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_staff.*
import th.ac.kku.cis.mobileapp.RepairDorm.Adapters.ListRequestAdapter
import th.ac.kku.cis.mobileapp.RepairDorm.Adapters.listReqForStaff
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoRequest
import th.ac.kku.cis.mobileapp.RepairDorm.Models.listRequestFB

class StaffActivity : AppCompatActivity() {


    private val TAG: String = "StaffActivity"
    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        Firebase.database.reference.child("repairlists")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val listView: ListView = findViewById(R.id.allRequest)
                    val dataReq = mutableListOf<listRequestFB>()

                    for (postSnapshot in p0.children){
                        var idstd = postSnapshot.key.toString()
                        for (eachRepairList in postSnapshot.children){

                            var key = eachRepairList.key.toString()
                            dataReq.add(
                                listRequestFB(
                                    eachRepairList.child("date").getValue().toString(),
                                    eachRepairList.child("description").getValue().toString(),
                                    eachRepairList.child("jobreq").getValue().toString(),
                                    eachRepairList.child("status").getValue().toString(),
                                    eachRepairList.child("topic").getValue().toString(),
                                    idstd,
                                    key
                                ))
                        }
                    }

                    listView.adapter = listReqForStaff(
                        this@StaffActivity, R.layout.activity_showlistforstaff, dataReq
                    )

                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectItemFromRequest = parent.getItemAtPosition(position) as listRequestFB

                        //Toast.makeText(this@StaffActivity, selectItemFromRequest.jobreq, Toast.LENGTH_LONG).show()

                        val l = Intent(this@StaffActivity, showReqForStaffActivity::class.java)
                        l.putExtra("date", selectItemFromRequest.date)
                        l.putExtra("descript", selectItemFromRequest.description)
                        l.putExtra("job", selectItemFromRequest.jobreq)
                        l.putExtra("status", selectItemFromRequest.status)
                        l.putExtra("topic", selectItemFromRequest.topic)
                        l.putExtra("stdid", selectItemFromRequest.idstudent)
                        l.putExtra("key", selectItemFromRequest.key)

                        startActivity(l)
                    }

                }
            })

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth!!.currentUser

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null){
                startActivity(Intent(this@StaffActivity, LoginStaffActivity::class.java))
                finish()
            }
        }


        btn_logout1.setOnClickListener{
            mAuth!!.signOut()
            Toast.makeText(this@StaffActivity, "ออกจากระบบแล้ว", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@StaffActivity, MainActivity::class.java))
            finish()
        }
    }
}
