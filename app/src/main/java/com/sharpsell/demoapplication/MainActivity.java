package com.sharpsell.demoapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enparadigm.sharpsell.sdk.ErrorListener;
import com.enparadigm.sharpsell.sdk.Sharpsell;
import com.enparadigm.sharpsell.sdk.SuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText userUniqueIdTv;
    private TextInputEditText userGroupIdTv;
    private TextInputEditText nameTv;
    private TextInputEditText emailTv;
    private TextInputEditText mobileTv;
    private TextInputEditText appCodeTv;
    private Button loginBtn;
    private View loginView;
    private View loggedInView;
    private Button openHomePageBtn;
    private Button logoutBtn;
    private Button profileBtn;
    private Button openPresentationBtn;
    private TextInputEditText presentationNameTv;
    private TextInputEditText presentationInputOneTv;
    private TextInputEditText presentationInputTwoTv;
    private Button openLaunchpadBtn, openMCDirectoryBtn, openPotdBtn, openDvcBtn, openTcBtn, openSbBtn, openQlBtn;
    public String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userUniqueIdTv = findViewById(R.id.user_unique_id_tv);
        userGroupIdTv = findViewById(R.id.user_group_id_tv);
        nameTv = findViewById(R.id.name_tv);
        emailTv = findViewById(R.id.email_tv);
        mobileTv = findViewById(R.id.mobile_tv);
        appCodeTv = findViewById(R.id.app_code_tv);
        loginBtn = findViewById(R.id.login_btn);
        loginView = findViewById(R.id.login_view);
        loggedInView = findViewById(R.id.logged_in_view);
        openHomePageBtn = findViewById(R.id.open_home_page);
        logoutBtn = findViewById(R.id.logout_btn);
        profileBtn = findViewById(R.id.profile_btn);
        openPresentationBtn = findViewById(R.id.open_presentation_button);
        presentationNameTv = findViewById(R.id.presentation_name_tv);
        presentationInputOneTv = findViewById(R.id.presentation_input_one_tv);
        presentationInputTwoTv = findViewById(R.id.presentation_input_two_tv);
        openLaunchpadBtn = findViewById(R.id.open_launchpad_btn);
        openMCDirectoryBtn = findViewById(R.id.mc_directory_btn);
        openPotdBtn = findViewById(R.id.potd_btn);
        openDvcBtn = findViewById(R.id.dvc_btn);
        openTcBtn = findViewById(R.id.tc_btn);
        openSbBtn = findViewById(R.id.sb_btn);
        openQlBtn = findViewById(R.id.ql_btn);

        loginBtn.setOnClickListener(v -> login());
        openHomePageBtn.setOnClickListener(v -> openHomePage());
        logoutBtn.setOnClickListener(v -> logout());
        profileBtn.setOnClickListener(v -> openProfile());
        openPresentationBtn.setOnClickListener(v -> openPresentation());
        openLaunchpadBtn.setOnClickListener(v -> openLaunchpad());
        openMCDirectoryBtn.setOnClickListener(v -> openMcDirectory());
        openPotdBtn.setOnClickListener(v -> openPotd());
        openDvcBtn.setOnClickListener(v -> openDvc());
        openTcBtn.setOnClickListener(v -> openTc());
        openSbBtn.setOnClickListener(v -> openProductBundle());
        openQlBtn.setOnClickListener(v -> openQuickLinks());

        showLoginView();
    }


    private void showLoggedInView() {
        loginView.setVisibility(View.GONE);
        loggedInView.setVisibility(View.VISIBLE);
    }

    private void showLoginView() {
        prefillLoginFields();
        loggedInView.setVisibility(View.GONE);
        loginView.setVisibility(View.VISIBLE);
    }

    private void prefillLoginFields() {
        userUniqueIdTv.setText("9999999999");
        userGroupIdTv.setText("410");
        nameTv.setText("Test User");
        emailTv.setText("test9@test9.com");
        mobileTv.setText("6666666666");
        appCodeTv.setText("dev");
    }

    private void login() {
        JSONObject data = new JSONObject();
        try {
            data.put("company_code", getText(appCodeTv));
            data.put("user_unique_id", getText(userUniqueIdTv));
            data.put("user_group_id", getInt(userGroupIdTv));
            data.put("country_code", null);
            data.put("user_meta", null);
            data.put("name", getText(nameTv));
            data.put("mobile_number", getText(mobileTv));
            data.put("email", getText(emailTv));
            data.put("fcm_token", fcmToken);

            Sharpsell.INSTANCE.initialize(
                    this,
                    data.toString(),
                    new SuccessListener() {
                        @Override
                        public void onSuccess() {
                            showLoggedInView();
                        }
                    },
                    new ErrorListener<String>() {
                        @Override
                        public void onError(@Nullable String error) {
                            Toast.makeText(MainActivity.this, "Initialization Failed : " + error, Toast.LENGTH_LONG).show();
                            Log.d("error", error);
                        }
                    }
            );
            Sharpsell.INSTANCE.enableLogsInProductionSdk(this, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openHomePage() {
        Sharpsell.INSTANCE.open(MainActivity.this, null);
    }

    private void openPresentation() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "product_presentation_input");
            data.put("presentation_name", getText(presentationNameTv));
            data.put("input_one", getText(presentationInputOneTv));
            data.put("input_two", getText(presentationInputTwoTv));
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openLaunchpad() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "launchpad");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openMcDirectory() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "mc_directory");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openPotd() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "potd");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openDvc() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "dvc");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openTc() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "tc_home");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openProductBundle() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "product_bundle");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openQuickLinks() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "quick_links");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openProfile() {
        JSONObject data = new JSONObject();
        try {
            data.put("route", "profile");
            Sharpsell.INSTANCE.open(this, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        Sharpsell.INSTANCE.clearData(this);
        showLoginView();
    }

    private String getText(TextView textView) {
        if (textView.getText() != null) {
            return textView.getText().toString();
        } else {
            return "";
        }
    }

    private int getInt(TextView textView) {
        try {
            if (textView.getText() != null) {
                return Integer.parseInt(textView.getText().toString());
            } else {
                return 0;
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}