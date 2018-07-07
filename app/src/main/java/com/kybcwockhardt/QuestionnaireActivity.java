package com.kybcwockhardt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.QuestionnaireAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.Doctor;
import com.kybcwockhardt.models.Patient;
import com.loopj.android.http.RequestParams;
import com.williamww.silkysignature.views.SignaturePad;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class QuestionnaireActivity extends CustomActivity implements CustomActivity.ResponseCallback, RadioGroup.OnCheckedChangeListener, CheckBox.OnCheckedChangeListener {

    private Toolbar toolbar;
    private ImageView img_signature;
    private Button btn_view_drafts;
    private Patient.Data currentPatient;
    private int patientPosition = 0;
    private RadioGroup radio_cough, radio_sputum, radio_breath, radio_qol_1, radio_qol_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_qustionnaires);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.questionnaire));
        currentPatient = SingleInstance.getInstance().getPatient();
        setupUiElements();

    }

    private void setupUiElements() {
        setTouchNClick(R.id.btn_view_drafts);
        setTouchNClick(R.id.btn_submit);
        setTouchNClick(R.id.btn_signature);
        img_signature = findViewById(R.id.img_signature);
        radio_qol_1 = findViewById(R.id.radio_qol_1);
        radio_qol_2 = findViewById(R.id.radio_qol_2);
        radio_cough = findViewById(R.id.radio_cough);
        radio_sputum = findViewById(R.id.radio_sputum);
        radio_breath = findViewById(R.id.radio_breath);

        radio_qol_1.setOnCheckedChangeListener(this);
        radio_qol_2.setOnCheckedChangeListener(this);
        radio_cough.setOnCheckedChangeListener(this);
        radio_sputum.setOnCheckedChangeListener(this);
        radio_breath.setOnCheckedChangeListener(this);
    }

    private int finalScore;
    private File signature = null;

    @Override
    public void onClick(View v) {
        super.onClick(v);

        finalScore = qol1Score + qol2Score + breathScore + sputumScore + coughScore;
        finalScore = 4 * finalScore;

        if (v.getId() == R.id.btn_submit) {
            final String contentType = RequestParams.APPLICATION_OCTET_STREAM;
            RequestParams p = new RequestParams();

            p.put("patient_id", currentPatient.getId());
            String symptoms = "";
            symptoms += getValue(coughScore);
            symptoms += getValue(sputumScore);
            symptoms += getValue(breathScore);
            symptoms = symptoms.substring(0, symptoms.length() - 1);
            p.put("symtoms", symptoms);

            String qolScore = "";
            qolScore += getValue(qol1Score);
            qolScore += getValue(qol2Score);
            qolScore = qolScore.substring(0, qolScore.length() - 1);
            p.put("qol", qolScore);
            p.put("score", finalScore);
            try {
                p.put("signature", signature, contentType);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            postCall(getContext(), BASE_URL + "create-question", p, "Saving Questionnaire...", 1);
            patientUpdatedData = new Patient();
        } else if (v.getId() == R.id.btn_signature) {
            openSignaturePanel();
        } else if (v.getId() == R.id.btn_view_drafts) {
            startActivity(new Intent(getContext(), DraftsActivity.class));
        }
    }

    private String getValue(int score) {
        switch (score) {
            case 1:
                return "Never,";
            case 2:
                return "Rarely,";
            case 3:
                return "Some of the time,";
            case 4:
                return "Most of the time,";
            case 5:
                return "Always,";
            default:
                return "Never,";
        }
    }

    private Context getContext() {
        return QuestionnaireActivity.this;
    }

    private Bitmap signatureBitmap = null;

    private void openSignaturePanel() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.signature_dialog);

        final SignaturePad mSignaturePad = dialog.findViewById(R.id.signature_pad);
        final Button btn_clear = dialog.findViewById(R.id.btn_clear);
        final Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureBitmap = mSignaturePad.getSignatureBitmap();
                img_signature.setImageBitmap(signatureBitmap);
                signature = new File(getContext().getCacheDir(), "sign.jpg");
                try {
                    signature.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//Convert bitmap to byte array
                Bitmap bitmap = signatureBitmap;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(signature);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                img_signature.setImageBitmap(null);
                mSignaturePad.clear();
            }
        });
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {
            }
        });

        dialog.show();
    }

    Patient patientUpdatedData;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio_cough_1) {
            coughScore = 1;
        } else if (checkedId == R.id.radio_cough_2) {
            coughScore = 2;
        } else if (checkedId == R.id.radio_cough_3) {
            coughScore = 3;
        } else if (checkedId == R.id.radio_cough_4) {
            coughScore = 4;
        } else if (checkedId == R.id.radio_cough_5) {
            coughScore = 5;
        } else if (checkedId == R.id.radio_sputum_1) {
            sputumScore = 1;
        } else if (checkedId == R.id.radio_sputum_2) {
            sputumScore = 2;
        } else if (checkedId == R.id.radio_sputum_3) {
            sputumScore = 3;
        } else if (checkedId == R.id.radio_sputum_4) {
            sputumScore = 4;
        } else if (checkedId == R.id.radio_sputum_5) {
            sputumScore = 5;
        } else if (checkedId == R.id.radio_breath_1) {
            breathScore = 1;
        } else if (checkedId == R.id.radio_breath_2) {
            breathScore = 2;
        } else if (checkedId == R.id.radio_breath_3) {
            breathScore = 3;
        } else if (checkedId == R.id.radio_breath_4) {
            breathScore = 4;
        } else if (checkedId == R.id.radio_breath_5) {
            breathScore = 5;
        } else if (checkedId == R.id.radio_qol_11) {
            qol1Score = 1;
        } else if (checkedId == R.id.radio_qol_12) {
            qol1Score = 2;
        } else if (checkedId == R.id.radio_qol_13) {
            qol1Score = 3;
        } else if (checkedId == R.id.radio_qol_14) {
            qol1Score = 4;
        } else if (checkedId == R.id.radio_qol_15) {
            qol1Score = 5;
        } else if (checkedId == R.id.radio_qol_21) {
            qol2Score = 1;
        } else if (checkedId == R.id.radio_qol_22) {
            qol2Score = 2;
        } else if (checkedId == R.id.radio_qol_23) {
            qol2Score = 3;
        } else if (checkedId == R.id.radio_qol_24) {
            qol2Score = 4;
        } else if (checkedId == R.id.radio_qol_25) {
            qol2Score = 5;
        }
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            Patient.Question q = new Gson().fromJson(o.optJSONObject("data").toString(), Patient.Question.class);
            if (patientPosition != -1) {
                Camp.Data data = SingleInstance.getInstance().getSelectedCamp();
                data.getPatients().get(patientPosition).setQuestion(q);
                SingleInstance.getInstance().setSelectedCamp(data);
                SingleInstance.getInstance().setCurrentQuestionReport(q);
                startActivity(new Intent(getContext(), SubmitQuestionnaireActivity.class));
                finish();
            } else {
                SingleInstance.getInstance().setCurrentQuestionReport(q);
                startActivity(new Intent(getContext(), SubmitQuestionnaireActivity.class));
                finish();
            }
        } else {
            MyApp.popMessage("Error", "Some error while submission, Please retry to submit questionnaire.", getContext());

        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {
        MyApp.popMessage("Error", error, getContext());
    }

    private int coughScore = 1;
    private int sputumScore = 1;
    private int breathScore = 1;
    private int qol1Score = 1;
    private int qol2Score = 1;
}
