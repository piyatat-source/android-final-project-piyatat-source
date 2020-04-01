package th.ac.kku.cis.mobileapp.RepairDorm.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class listRequestFB (
    var date: String? = "",
    var description: String? = "",
    var jobreq: String? = "",
    var status: String? = "",
    var topic: String? = "",
    var idstudent: String = "",
    var key: String = ""
)