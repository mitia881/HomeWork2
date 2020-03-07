package com.wau.android.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Contact.Contact;


public class EditContactActivity extends AppCompatActivity {
    private Button remove;
    private EditText nameEditView, numberEmailEditView;

    private Contact contact = new Contact();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        setToolbarSetting();

        nameEditView = findViewById(R.id.nameEditText);
        numberEmailEditView = findViewById(R.id.phoneEmailEditText);

        // Получаем индекс контакта для редактирования
        final int index = getIntent().getIntExtra(ContactViewActivity.EDIT_INDEX_CONTACT_KEY, 0);
        // Получаем заполненный контакт для редактирования
        contact = (Contact) getIntent().getSerializableExtra(ContactViewActivity.EDIT_CONTACT_KEY);
        Log.d("BUNDLE", contact.toString());

        nameEditView.setText(contact.getName());
        numberEmailEditView.setText(contact.getNumberOrEmail());

        remove = findViewById(R.id.removeButton);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String contactName = nameEditView.getText().toString();
//                String contactNumberOrEmail = numberEmailEditView.getText().toString();
//                if (isContactNameCorrect(contactName, contactNumberOrEmail)) {
//                    contact.setName(contactName);
//                    contact.setNumberOrEmail(contactNumberOrEmail);
                Intent data = new Intent();
                data.putExtra(ContactViewActivity.REMOVE_CONTACT_INDEX, index);
                data.putExtra(ContactViewActivity.REMOVE_CONTACT, contact);
                Log.d("BUNDLE", data.toString());
                setResult(RESULT_OK, data);
                finish();
//                }
            }
        });
    }

    // Устанавливаем настройки Toolbar
    private void setToolbarSetting() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.edit_contact);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}