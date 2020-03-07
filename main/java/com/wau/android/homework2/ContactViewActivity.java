package com.wau.android.homework2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Contact.Contact;
import Contact.ContactAdapter;

public class ContactViewActivity extends AppCompatActivity implements ContactAdapter.ContactAdapterListener {
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactArrayList;
    private ContactAdapter contactAdapter;
    private SearchView searchView;

    static final String ADD_CONTACT_KEY = "ADD_CONTACT";
    static final String EDIT_INDEX_CONTACT_KEY = "EDIT_INDEX_CONTACT";
    static final String EDIT_CONTACT_KEY = "EDIT_CONTACT";
    static final int RESULT_DELETE = 666;
    static final String NEW_CONTACT = "NEW_CONTACT";
    static final String REMOVE_CONTACT = "REMOVE_CONTACT";
    static final String REMOVE_CONTACT_INDEX = "REMOVE_CONTACT_INDEX";
    private static final int REQUEST_ACCESS_TYPE = 1;  // Для добавления
    private static final int REQUEST_ACCESS_TYPE_2 = 2; // Для удаления/изменения контакта

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        recyclerView = findViewById(R.id.recyclerView);
        contactArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contactArrayList, this);

        // проверка ориентации экрана для отрисовки
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.setAdapter(contactAdapter);


        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // передаём в новую активити контакт для заполнения
                Intent intent = new Intent(ContactViewActivity.this, AddContactActivity.class);
                intent.putExtra(ADD_CONTACT_KEY, new Contact());
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
            }
        });

        // Делаю ассоциацию конфигурации поиска со своей SearchView
        searchViewConfig();

        setTestData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Проверка заполненности массива и выбор фона
        if (!contactArrayList.isEmpty()) {
            findViewById(R.id.nonElementsTextView).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ACCESS_TYPE:
                if (resultCode == RESULT_OK) {
                    Contact newContact = (Contact) data.getSerializableExtra(NEW_CONTACT);
                    Log.d("IS_OK", newContact.toString());
                    contactArrayList.add(newContact);
                    contactAdapter.notifyDataSetChanged();
                } else {
                    Log.d("ERROR", "Ошибка доступа");
                }
                break;
            case REQUEST_ACCESS_TYPE_2:
                if (resultCode == RESULT_OK) {
                    int removeContactIndex = data.getIntExtra(REMOVE_CONTACT_INDEX, 0);
//                    Contact removeContact = (Contact) data.getSerializableExtra(REMOVE_CONTACT);
//                    Log.d("IS_OK", removeContact.toString());
                    contactArrayList.remove(removeContactIndex);
                    contactAdapter.notifyDataSetChanged();
                } else {
                    Log.d("ERROR", "Ошибка доступа");
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void searchViewConfig() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Вешаю листенер на изменение текста
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void setTestData() {
        contactArrayList.add(new Contact("D", "1111111", R.drawable.is_contact_phone_purple_24dp));
        contactArrayList.add(new Contact("PaK", "2222222", R.drawable.ic_contact_mail_pink_24dp));
        contactArrayList.add(new Contact("tem", "3333333", R.drawable.is_contact_phone_purple_24dp));
        contactArrayList.add(new Contact("Dmon", "4444444", R.drawable.ic_contact_mail_pink_24dp));
        contactArrayList.add(new Contact("Lea", "5555555", R.drawable.ic_contact_mail_pink_24dp));
    }

    @Override
    public void onContactSelected(int index, Contact contact) {
//        Toast.makeText(getApplicationContext(), "Selected: "
//                + contact.getName() + ", "
//                + contact.getNumberOrEmail(), Toast.LENGTH_LONG)
//                .show();
        Log.d("PUT", index + "");
        Log.d("PUT", contact.toString());
        Intent intent = new Intent(ContactViewActivity.this, EditContactActivity.class);
        intent.putExtra(EDIT_INDEX_CONTACT_KEY, index);
        intent.putExtra(EDIT_CONTACT_KEY, contact);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE_2);
    }
}
