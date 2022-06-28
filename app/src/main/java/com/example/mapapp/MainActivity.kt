package com.example.mapapp


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


const val RC_SIGN_IN = 123;


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)


        signInButton.visibility = View.VISIBLE
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        };

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
//            val intent = Intent(this, mapScreen::class.java)
//            startActivity(intent);

        updateUI();
        /* Signed in successfully, show authenticated UI. */


    } catch (e: ApiException) {
        // The ApiException status code indicates the detailed failure reason.
        // Please refer to the GoogleSignInStatusCodes class reference for more information.
         updateUI();

        // update ui jest tutaj z powodu braku możliwości użycia signed apk w emulatorze. logowanie działa na telefonie

    }
    fun updateUI(){
//        setContentView(R.layout.activity_map_screen)
        val i = Intent(this@MainActivity, MapsActivity::class.java)
        startActivity(i)
    }
}