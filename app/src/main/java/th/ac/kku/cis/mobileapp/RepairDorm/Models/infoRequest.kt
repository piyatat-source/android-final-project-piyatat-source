package th.ac.kku.cis.mobileapp.RepairDorm.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class infoRequest(
    var Topic: String? = "",
    var Date: String? = "",
    var Jobreq: String? = "",
    var Description: String? = "",
    var Status: String? = "รอดำเนินการ"

)