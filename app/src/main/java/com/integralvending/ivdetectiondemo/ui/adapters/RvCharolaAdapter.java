package com.integralvending.ivdetectiondemo.ui.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.integralvending.ivdetectiondemo.databinding.RowCharolaBinding;
import com.integralvending.ivdetectiondemo.models.MCharola;

import java.util.ArrayList;

public class RvCharolaAdapter extends RecyclerView.Adapter<RvCharolaAdapter.ViewHolder> {
    private static ArrayList<MCharola> data;

    public RvCharolaAdapter(ArrayList<MCharola> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowCharolaBinding binding = RowCharolaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MCharola dataCharola = data.get(position);
        holder.bind(dataCharola, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(MCharola newData) {
        data.add(newData);
        notifyDataSetChanged();
    }

    public ArrayList<MCharola> getData() {
        return data;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowCharolaBinding binding;

        public ViewHolder(RowCharolaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MCharola dataCharola, int position) {
            binding.tvIdRectangulo.setText(dataCharola.getIdRectangulo() + "");
            binding.tvNumeroproducto.setText(dataCharola.getNumero() + "");
            binding.tvNumeronuevoproducto.setText(String.valueOf(dataCharola.getNuevonumero()));
            binding.tvNumeronuevoproducto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().isEmpty()) return;

                    MCharola charolaUpdated = dataCharola;
                    charolaUpdated.setNumero(Integer.parseInt(s.toString()));
                    data.set(position, charolaUpdated);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
