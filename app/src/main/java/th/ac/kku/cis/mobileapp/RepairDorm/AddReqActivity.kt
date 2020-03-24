package th.ac.kku.cis.mobileapp.RepairDorm


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addreq.*
import kotlinx.android.synthetic.main.content_student2.*
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoRequest
import java.text.SimpleDateFormat
import java.util.*


class AddReqActivity : AppCompatActivity() {

    var selectJob :String? = ""

    private val TAG: String = "AddReq"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addreq)

//        var i = intent
//        var Firstname = i.getStringExtra("firstname")
//        var Lastname = i.getStringExtra("lastname")
//        tvFullname.text = "คุณ " + Firstname + " " + Lastname

        val date: String = SimpleDateFormat("dd/MM/yyyy").format(Date())
        tv_date.text = date

        val job = arrayOf("กรุณาเลือกประเภทงานซ่อม",
                          "งานไฟฟ้าหอพักนักศึกษา",
                          "งานระบบสุขาภิบาลหอพักนักศึกษา",
                          "งานระบบประปาหอพักนักศึกษา",
                          "งานซ่อมบำรุงห้อง และครุภัณฑ์หอพักนักศึกษา")

        val jobAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,job)
        tb_jobreq.adapter = jobAdapter

        tb_jobreq.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var job = job[position]
                if (job == "กรุณาเลือกประเภทงานซ่อม") {
                    selectJob = ""
                } else {
                    selectJob = job
                }
                Log.d(TAG, "Your request job is " + selectJob.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        btn_save.setOnClickListener {
            saveData(date,selectJob)
        }
    }


    private fun saveData(date: String, selectJob: String?) {
        //var date: String = SimpleDateFormat("yyyy/MM/dd").format(Date())

        val topic = tb_topic.text.toString().trim()
        val description = tb_description.text.toString().trim()
        val jobReq = selectJob


        if (topic.isEmpty()){
            Toast.makeText(this,"Please enter your topic",Toast.LENGTH_LONG).show()
            return
        }

        if (jobReq!!.isEmpty()){
            Toast.makeText(this,"Please enter your job request",Toast.LENGTH_LONG).show()
            return
        }

        if (description.isEmpty()){
            Toast.makeText(this,"Please enter your description",Toast.LENGTH_LONG).show()
            return
        }


        val myDataBase = FirebaseDatabase.getInstance().getReference("repairlists")
        val id = myDataBase.push().key

        val req = infoRequest(topic,date,jobReq,description)
        if (id != null) {
            myDataBase.child(id).setValue(req).addOnCompleteListener{
                Toast.makeText(this,"ส่งคำร้องเรียบร้อยแล้ว  ",Toast.LENGTH_LONG).show()
            }
        }
    }
}
