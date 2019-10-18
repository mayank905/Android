package com.lionsden.ezytoll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * A Registration Screen that offers registering of name/email/password.
 */

public class Registration extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView,mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.submit);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the submit attempt.
        String Username = mUsernameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        //Check for Username,if the user entered one
        if (TextUtils.isEmpty(Username)) {
            mUsernameView.setError(getString(R.string.error_field_required1));
            focusView = mUsernameView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(Username,email, password);
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, String>
    {

        private final String mEmail;
        private final String mUsername;
        private final String mPassword;

        private final String TAG = UserLoginTask.class.getSimpleName();
        UserLoginTask(String Username,String email, String password) {
            mUsername=Username;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... arg0) {
            try{

                String link="http://192.168.0.7/registration.php";
                String data =URLEncoder.encode("username", "UTF-8")
                        +"="+URLEncoder.encode(mUsername, "UTF-8");
                data +="&"+URLEncoder.encode("password", "UTF-8")
                        +"="+URLEncoder.encode(mPassword, "UTF-8");
                data +="&"+URLEncoder.encode("email", "UTF-8")
                        +"="+URLEncoder.encode(mEmail, "UTF-8");
                HttpHandler h=new HttpHandler(link,data);
                String result=h.makeServiceCall();
                return result;

            }catch(Exception e){
                Log.e(TAG, e.getMessage());
                //Toast.makeText(getApplicationContext(),"Error while connecting to server",Toast.LENGTH_LONG).show();
                return "F";
            }
        }
        // TODO: attempt authentication against a network service.


        // TODO: register the new account here.



        protected void onPostExecute(String result) {
            mAuthTask = null;
            showProgress(false);

            switch(result) {
                case "A" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"Email already exists and verified",Toast.LENGTH_LONG).show();
                    break; // optional

                case "B" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"Verification mail is resent .Check your mail",Toast.LENGTH_LONG).show();
                    break; // optional
                case "C" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"Email exists but verification mail coudn't be resend",Toast.LENGTH_LONG).show();
                    break; // optional
                case "D" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"A Verification mail is send to your email id ",Toast.LENGTH_LONG).show();
                    //Intent I = new Intent(getApplicationContext(), LoginActivity.class);
                    // get user input

                    // pass user input in the intent


                    //startActivity(I);
                    break; // optional
                case "E" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"Verification mail coudn't be send ,try again",Toast.LENGTH_LONG).show();
                    break; // optional
                case "F" :
                    // Statements
                    Toast.makeText(getApplicationContext(),"Some Exception occurred in background Task",Toast.LENGTH_LONG).show();
                    break; // optional

                // You can have any number of case statements.
                default : // Optional
                    // Statements
                    Toast.makeText(getApplicationContext(),"Don't know how this got printed",Toast.LENGTH_LONG).show();
            }
        }

    }
}
