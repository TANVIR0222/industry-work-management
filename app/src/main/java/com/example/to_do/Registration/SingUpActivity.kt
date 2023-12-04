package com.example.to_do.Registration

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do.BossMainActivity
import com.example.to_do.databinding.AccountDialogeBinding
import com.example.to_do.databinding.ActivitySingUpBinding
import com.example.to_do.utile.Users
import com.example.to_do.utile.utile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.tasks.await as await1

class SingUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingUpBinding

    // check box Boss || E
    private var userType: String = " "

    //  image  launch
    private var userImageUri: Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        userImageUri = it
        binding.profileImage.setImageURI(userImageUri)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // image launch
        binding.apply {
            profileImage.setOnClickListener {
                selectImage.launch("image/*")
            }
// check RadioButton
            binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                userType = findViewById<RadioButton>(checkedId).text.toString()
                Log.d("TAG", userType)

            }

            binding.singup.setOnClickListener {
                createUser()
            }
            // singIn activity going
            binding.tsing.setOnClickListener {
                startActivity(Intent(this@SingUpActivity, SingInActivity::class.java))
            }

        }


    }

    private fun createUser() {
        utile.showDialog(this)

        val name = binding.name.editText?.text.toString().trim()
        val email = binding.Semail.editText?.text.toString().trim()
        val password = binding.password.editText?.text.toString().trim()
        val conPass = binding.cpassword.editText?.text.toString().trim()

        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && conPass.isNotBlank()) {

            if (userImageUri == null) {
                utile.showToast(this, " Please select one image")


            } else if (password == conPass) {

            // check USer type null or not

                if(userType != " ")
                    uploadImageUri(name, password, email, conPass)

                else{
                    utile.hideDialog()
                    utile.showToast(this, " Select user type ")
                }
                // check USer type null or not
            } else {
                utile.showToast(this, "Password are not matching")
            }

        } else {
            utile.hideDialog()
            utile.showToast(this, " Empty Fields are not allowed ")
        }


    }

    private fun uploadImageUri(name: String, password: String, email: String, conPass: String) {

        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storage = FirebaseStorage.getInstance().getReference("Profile").child(currentUserUid)
            .child("Profile . jpg")


        lifecycleScope.launch {
            // storage
            try {

                val uploadTask = storage.putFile(userImageUri!!).await1()
                if (uploadTask.task.isSuccessful) {
                    val downloadUrl = storage.downloadUrl.await1()

                    saveUserData(name, email, password, downloadUrl)
                } else {
                    utile.hideDialog()
                    Toast.makeText(
                        this@SingUpActivity,
                        "${uploadTask.task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                utile.hideDialog()
                Toast.makeText(
                    this@SingUpActivity,
                    "Upload Failed : ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

    private suspend fun saveUserData(
        name: String,
        email: String,
        password: String,
        downloadUrl: Uri?
    ) {

// boss SingUp || Employee
        lifecycleScope.launch {
            val db = FirebaseDatabase.getInstance().getReference("Users")

            try {
                val firebaseAuth = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()


//        verify alertDialog box show

                if (firebaseAuth != null) {

                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {

                        val dialog = AccountDialogeBinding.inflate(LayoutInflater.from(this@SingUpActivity))

                        val alertDialog = AlertDialog.Builder(this@SingUpActivity)
                            .setView(dialog.root)
                            .create()
                        utile.hideDialog()
                        alertDialog.show()


                        dialog.okBtn.setOnClickListener {
                            alertDialog.dismiss()
                            startActivity(Intent(this@SingUpActivity , SingInActivity::class.java))
                            finish()
                        }


                    }



                    val uid = firebaseAuth.user?.uid.toString()
                    val saveUsertype = if (userType == "Boss") "Boss" else "Employee"

                    // object
                    val boss =
                        Users(saveUsertype, uid, name, password, email, downloadUrl.toString())
                    // save data
                    db.child(uid).setValue(boss).await()





                } else {
                    utile.hideDialog()
                    utile.showToast(this@SingUpActivity, "sing up Failed")
                }

            } catch (e: Exception) {
                utile.hideDialog()
                utile.showToast(this@SingUpActivity, e.message.toString())

            }
        }

    }
}