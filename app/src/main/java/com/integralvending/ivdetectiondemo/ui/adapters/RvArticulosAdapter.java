package com.integralvending.ivdetectiondemo.ui.adapters;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.integralvending.ivdetectiondemo.databinding.RowArticuloBinding;
import com.integralvending.ivdetectiondemo.models.MArticulo;

import java.util.ArrayList;

public class RvArticulosAdapter extends RecyclerView.Adapter<RvArticulosAdapter.ViewHolder> {

    private ArrayList<MArticulo> data;


    public RvArticulosAdapter(ArrayList<MArticulo> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowArticuloBinding binding = RowArticuloBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MArticulo dataArticulo = data.get(position);
        holder.bind(dataArticulo, position);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    public void addData(MArticulo newData){
        data.add(newData);
        notifyDataSetChanged();
    }
    public ArrayList<MArticulo> getData(){return data;}

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowArticuloBinding binding;

        public ViewHolder(RowArticuloBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MArticulo dataArticulo, int position) {
            binding.tvIdArticulo.setText(dataArticulo.getIdArticulo() + "");
            binding.tvNombreArticulo.setText(dataArticulo.getNombre());
            binding.tvCantidadArticulo.setText(dataArticulo.getCantidad() + "");

            binding.tvCantidadArticulo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }
    }
}
