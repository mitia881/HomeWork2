package com.wau.android.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Contact.Contact;


public class AddContactActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton phoneRadioButton;
    RadioButton emailRadioButton;

    EditText nameEditView, numberEmailEditView;

    private Contact contact = new Contact();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

       // setToolbarSetting();

        radioGroup = findViewById(R.id.radioGroup);
        phoneRadioButton = findViewById(R.id.phoneRadioButton);
        emailRadioButton = findViewById(R.id.emailRadioButton);

        nameEditView = findViewById(R.id.nameEditText);
        numberEmailEditView = findViewById(R.id.phoneEmailEditText);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.phoneRadioButton:
                        numberEmailEditView.setHint(R.string.phone_number);
                        numberEmailEditView.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case R.id.emailRadioButton:
                        numberEmailEditView.setHint(R.string.email);
                        numberEmailEditView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;

                }
            }
        });

        // Получаем пустой контакт для заполнения
        contact = (Contact) getIntent().getSerializableExtra(ContactViewActivity.ADD_CONTACT_KEY);
        Log.d("BUNDLE", contact.toString());

    }

    // Устанавливаем настройки Toolbar
    private void setToolbarSetting() {
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.add_contact);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm:
                String contactName = nameEditView.getText().toString();
                String contactNumberOrEmail = numberEmailEditView.getText().toString();
                if (isContactCorrect(contactName, contactNumberOrEmail)) {
                    contact.setName(contactName);
                    contact.setNumberOrEmail(contactNumberOrEmail);
                    if (phoneRadioButton.isChecked()) {
                        contact.setImageId(R.drawable.is_contact_phone_purple_24dp);
                    } else {
                        contact.setImageId(R.drawable.ic_contact_mail_pink_24dp);
                    }
                    Intent data = new Intent();
                    data.putExtra(ContactViewActivity.NEW_CONTACT, contact);
                    Log.d("BUNDLE", data.toString());
                    setResult(RESULT_OK, data);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isContactCorrect(String contactName, String numberOrEmail) {
        if (contactName.equals("")) {
            showMessage("User name must not be empty!");
            return false;
        }

        if (phoneRadioButton.isChecked()) {
            if (!isPhoneCorrect(numberOrEmail)) {
                return false;
            }
        } else {
            if (!isEmailCorrect(numberOrEmail)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPhoneCorrect(String phone) {
        if (android.util.Patterns.PHONE.matcher(phone).matches()) {
            return true;
        }
        showMessage("Phone is incorrect! Check your input!");
        return false;
    }

    private boolean isEmailCorrect(String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        showMessage("Email is incorrect! Check your email!");
        return false;
    }

    public void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 160);
        toast.show();
    }
}
