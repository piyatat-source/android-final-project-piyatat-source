package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student2.*
import kotlinx.android.synthetic.main.content_student2.*
import th.ac.kku.cis.mobileapp.RepairDorm.Adapters.ListRequestAdapter
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoRequest
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoStudent

class StudentActivity2 : AppCompatActivity() {

    private val TAG: String = "stdAct2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student2)
        setSupportActionBar(toolbar)

        var i = intent
        var Stdid = i.getStringExtra("stdid")
        var Firstname = i.getStringExtra("firstname")
        var Lastname = i.getStringExtra("lastname")
        var numID = i.getStringExtra("numid")
        var roomNo = i.getStringExtra("roomid")


        tvFullname.text = "คุณ " + Firstname + " " + Lastname


        Firebase.database.reference.child("repairlists").child(Stdid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val listView: ListView = findViewById(R.id.listViewRequest)
                    val dataReq = mutableListOf<infoRequest>()

                    for (postSnapshot in dataSnapshot.children) {
                        var key = postSnapshot.key.toString()
                        var stdid = Stdid
                        dataReq.add(
                            infoRequest(
                                postSnapshot.child("topic").getValue().toString()
                                , postSnapshot.child("date").getValue().toString()
                                , postSnapshot.child("jobreq").getValue().toString()
                                , postSnapshot.child("description").getValue().toString()
                                , postSnapshot.child("status").getValue().toString()
                                , key
                                , stdid
                            )
                        )
                    }

                    listView.adapter = ListRequestAdapter(
                        this@StudentActivity2, R.layout.activity_requestlist, dataReq
                    )

                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectItemFromRequest = parent.getItemAtPosition(position) as infoRequest
                        //val selectItemFromStudent = parent.getItemAtPosition(position) as infoStudent


                        val l = Intent(this@StudentActivity2, showReqActivity::class.java)
                        l.putExtra("topic", selectItemFromRequest.Topic)
                        l.putExtra("date", selectItemFromRequest.Date)
                        l.putExtra("job", selectItemFromRequest.Jobreq)
                        l.putExtra("descript", selectItemFromRequest.Description)
                        l.putExtra("status", selectItemFromRequest.Status)
                        l.putExtra("stdid", Stdid)

                        //l.putExtra("firstname", selectItemFromStudent.Firstname)
                        //l.putExtra("lastname", selectItemFromStudent.Lastname)

                        //Log.d(TAG, "Your firstname is " + Firstname)
                        startActivity(l)

                        //Toast.makeText(this@StudentActivity2, Stdid, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })





        fab.setOnClickListener { view ->
            Snackbar.make(view, "เพิ่มรายการแจ้งซ่อมหอพัก", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            //startActivity(Intent(this, AddReqActivity::class.java))

            Firebase.database.reference.child("students").child(Stdid)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(p0: DataSnapshot) {
                            val d = Intent(this@StudentActivity2, AddReqActivity::class.java)
                            d.putExtra("stdid", Stdid)
                            startActivity(d)

                    }
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented")
                    }
                })
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logoutmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Stdlogout -> {
                Toast.makeText(this, "ออกจากระบบแล้ว", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginStudentActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

