package com.mapyo.autovalueparcelsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.first_name_text)
    EditText firstNameEditText;
    @BindView(R.id.last_name_text)
    EditText lastNameEditText;
    @BindView(R.id.email_text)
    EditText emailEditText;
    @BindView(R.id.confirm_button)
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.confirm_button)
    void submit() {
        User user = User.builder()
                .firstName(firstNameEditText.getText().toString())
                .lastName(lastNameEditText.getText().toString())
                .email(emailEditText.getText().toString())
                .build();

        Intent intent = ConfirmActivity.intentBuilder(this, user).build();
        startActivity(intent);
    }
}
