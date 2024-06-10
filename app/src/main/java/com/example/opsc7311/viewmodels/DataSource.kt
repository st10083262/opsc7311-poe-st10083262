package com.example.opsc7311.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.opsc7311.R
import com.example.opsc7311.ui.models.Timesheet
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

fun GetTimesheetsFromFirestore(): SnapshotStateList<Timesheet> {

    val timesheets: SnapshotStateList<Timesheet> = mutableStateListOf()
    val db = Firebase.firestore
    db.collection("timesheets").get()
        .addOnSuccessListener { querySnapshot ->
            // Handle the success case
            val userList = mutableListOf<Timesheet>()
            for (document in querySnapshot.documents) {
                val user = document.toObject(Timesheet::class.java)
                user?.let { timesheets.add(it) }
            }
        }
        .addOnFailureListener { exception ->
            // Handle the failure case
        }

    return timesheets
}