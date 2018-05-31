package io.github.gc.sagitta

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.databinding.DataBindingUtil
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 77
    var mAuth = FirebaseAuth.getInstance()
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    //    var binding: ActivityLoginBinding? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        initFirebase()
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { }

        val fAuth = FirebaseAuth.getInstance()
        fAuth.signOut()
    }

    fun signIn(view: View) {
        showMessage(view, "Authenticating...")

        var email = "a@b.com"
        var pass = "123456"

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", mAuth.currentUser?.email)
                startActivity(intent)
            } else {
                showMessage(view, "Error: ${task.exception?.message}")
            }
        })

    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }

    private fun dataBinding() {
        val b = findViewById(R.id.gButton) as SignInButton

        b.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
            Log.d("login", "google!!!")
        }
    }

}

