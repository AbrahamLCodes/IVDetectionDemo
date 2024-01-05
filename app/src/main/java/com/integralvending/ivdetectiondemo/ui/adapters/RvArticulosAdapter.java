package com.integralvending.ivdetectiondemo.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.databinding.RowArticuloBinding;
import com.integralvending.ivdetectiondemo.ui.models.MArticulo;

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
        holder.bind(dataArticulo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowArticuloBinding binding;

        public ViewHolder(RowArticuloBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MArticulo dataArticulo) {
            binding.tvIdArticulo.setText(dataArticulo.getIdArticulo() + "");
            binding.tvNombreArticulo.setText(dataArticulo.getNombre());
            binding.tvCantidadArticulo.setText(dataArticulo.getCantidad() + "");
        }
    }
}
