package com.example.jplab4.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jplab4.R;
import com.example.jplab4.retrofit.entities.randomuser.RandomUser;

import java.util.List;

public class ListaContactsAdapter extends RecyclerView.Adapter<ListaContactsAdapter.ContactViewHolder>{
    private List<RandomUser> contacts;
    private Context context;

    public ListaContactsAdapter(List<RandomUser> contacts, Context context){
        this.contacts=contacts;
        this.context=context;
    }

    public List<RandomUser> getContacts() {
        return contacts;
    }

    public void setContacts(List<RandomUser> contacts) {
        this.contacts = contacts;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.card_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        RandomUser contact=contacts.get(position);
        holder.contact=contact;
        TextView tvNombre =holder.itemView.findViewById(R.id.tvNombre);
        TextView tvGenero =holder.itemView.findViewById(R.id.tvGenero);
        TextView tvCiudad =holder.itemView.findViewById(R.id.tvCiudad);
        TextView tvPais =holder.itemView.findViewById(R.id.tvPais);
        TextView tvEmail =holder.itemView.findViewById(R.id.tvEmail);
        TextView tvPhone =holder.itemView.findViewById(R.id.tvPhone);
        ImageView imageView= holder.itemView.findViewById(R.id.imgView);

        tvNombre.setText(contact.getName().getTitle() + " "+ contact.getName().getFirst()+" "+ contact.getName().getLast());
        tvGenero.setText("Género: "+contact.getGender());
        tvCiudad.setText("Ciudad: "+contact.getLocation().getCity());
        tvPais.setText("País: "+contact.getLocation().getCountry());
        tvEmail.setText("Email: "+contact.getEmail());
        tvPhone.setText("Phone: "+contact.getPhone());

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(contact.getPicture().getLarge()) // URL de la imagen
                .apply(requestOptions) // Aplica las opciones de carga
                .into(imageView); // ImageView de destino
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        RandomUser contact;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
