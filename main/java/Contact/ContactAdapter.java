package Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wau.android.homework2.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Contact> contactArrayList,
            contactArrayListFiltered;

    private ContactAdapterListener listener;

    public ContactAdapter(Context context, ArrayList<Contact> contacts, ContactAdapterListener listener) {
        this.context = context;
        this.contactArrayList = contacts;
        this.contactArrayListFiltered = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {//не понял
        final Contact contact = contactArrayListFiltered.get(position);
        holder.imageView.setImageResource(contact.getImageId());
        holder.nameView.setText(contact.getName());
        holder.numberOrEmailView.setText(contact.getNumberOrEmail());
    }

    @Override
    public int getItemCount() {
        return contactArrayListFiltered != null ? contactArrayListFiltered.size() : 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView, numberOrEmailView;

        public ContactViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.contactItemImageView);
            nameView = view.findViewById(R.id.contactNameTextView);
            numberOrEmailView = view.findViewById(R.id.contactNumberOrEmailTextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contact = contactArrayListFiltered.get(getAdapterPosition());
                    listener.onContactSelected(contactArrayList.indexOf(contact), contact);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                // Проверка введнных в поиск данных
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactArrayListFiltered = contactArrayList;
                } else {
                    ArrayList<Contact> filteredList = new ArrayList<>();
                    for (Contact row : contactArrayList) {
                        // Сравнение по имени и добавление в отфильтрованный массив
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactArrayListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactArrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactArrayListFiltered = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactAdapterListener {
        void onContactSelected(int index, Contact contact);
    }
}
