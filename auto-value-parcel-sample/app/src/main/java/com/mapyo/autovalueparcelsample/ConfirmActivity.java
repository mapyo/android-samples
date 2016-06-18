package com.mapyo.autovalueparcelsample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity {
    static final String BUNDLE_KEY_USER = "user";

    @BindView(R.id.first_name_text)
    TextView firstNameText;
    @BindView(R.id.last_name_text)
    TextView lastNameText;
    @BindView(R.id.email_text)
    TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(BUNDLE_KEY_USER);

        firstNameText.setText(user.firstName());
        lastNameText.setText(user.lastName());
        emailText.setText(user.email());
    }

    static IntentBuilder intentBuilder(Context context, User user) {
        return new IntentBuilder(context, user);
    }

    static class IntentBuilder {
        private Context context;
        private User user;

        IntentBuilder(Context context, User user) {
            this.context = context;
            this.user = user;
        }

        Intent build() {
            Intent intent = new Intent(context, ConfirmActivity.class);
            intent.putExtra(BUNDLE_KEY_USER, user);
            return intent;
        }
    }
}
