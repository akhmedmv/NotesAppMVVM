package com.akhmedmv.notesappmvvm.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.akhmedmv.notesappmvvm.database.DataBaseRepository
import com.akhmedmv.notesappmvvm.model.Note
import com.akhmedmv.notesappmvvm.utils.Constants
import com.akhmedmv.notesappmvvm.utils.FIREBASE_ID
import com.akhmedmv.notesappmvvm.utils.LOGIN
import com.akhmedmv.notesappmvvm.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppFirebaseRepository : DataBaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference
        .child(mAuth.currentUser?.uid.toString())
    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(
        note: Note,
        onSuccess: () -> Unit,
    ) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                Log.e("checkData", "Failed to add new note:")
            }
    }

    override suspend fun update(
        note: Note,
        onSuccess: () -> Unit
    ) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to add new note") }
    }

    override suspend fun delete(
        note: Note,
        onSuccess: () -> Unit
    ) {
        database.child(note.firebaseId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to add new note") }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDataBase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}