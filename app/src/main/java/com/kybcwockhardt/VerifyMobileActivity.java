package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kybcwockhardt.application.MyApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static java.sql.Types.NULL;

public class VerifyMobileActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private String mb_no;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String phoneNumber;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Verify Mobile Number");
//        toolbar.setBackgroundResource(NULL);
        setResponseListener(this);

        userName = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");

        mb_no = "+91" + phoneNumber;
        setupUiElement();
        showCounter();
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        if (code == null || code.isEmpty()) {
            MyApp.showMassage(getContext(), "Enter OTP please");
            return;

        }
//            Intent intent = new Intent(getContext(), GeneratePasswordActivity.class);
//            startActivity(intent);
//            return;

        try {
            // [START verify_with_code]
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
        }
    }

    private void setupUiElement() {

        setTouchNClick(R.id.btn_login);

        final PinEntryEditText pinEntry = findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    enteredPin = str.toString();
                    verifyPhoneNumberWithCode(mVerificationId, enteredPin);
                }
            });
        }
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            verifyPhoneNumberWithCode(mVerificationId, enteredPin);
        }

    }

    private String enteredPin = "";

    private void showCounter() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d("phone", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("phone", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("phone", "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;

                // ...
            }
        };

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                Log.d("authStateChange", "");
//            }
//        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mb_no,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("phone", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseAuth.getInstance().signOut();

                            Intent intent = new Intent(getContext(), GeneratePasswordActivity.class);
                            intent.putExtra("name", userName);
                            intent.putExtra("phone", phoneNumber);
                            startActivity(intent);
                            finish();
                            return;


                        } else {
//                            if (isForgot ) {
//                                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
//                                intent.putExtra("name", userName);
//                                intent.putExtra(AppConstant.EXTRA_2, phoneNumber);
//                                startActivity(intent);
//                                return;
//                            }
                            // Sign in failed, display a message and update the UI
                            Log.w("phone", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                MyApp.popMessage("Alert!", "Please enter a valid code that sent to " +
                                        " " + mb_no + ".\nThank you", getContext());
                            }
                        }
                    }
                });
    }


    private Context getContext() {
        return VerifyMobileActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
