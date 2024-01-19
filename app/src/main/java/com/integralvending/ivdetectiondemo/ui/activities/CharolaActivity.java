package com.integralvending.ivdetectiondemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.data.MockData;
import com.integralvending.ivdetectiondemo.databinding.ActivityCharolaBinding;
import com.integralvending.ivdetectiondemo.models.MCharola;
import com.integralvending.ivdetectiondemo.ui.adapters.RvCharolaAdapter;

import java.util.ArrayList;

public class CharolaActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCharolaBinding binding;

    private ArrayList<MCharola> charolaList = new ArrayList<>();
    private RvCharolaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCharolaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();

    }

    public void onClick(View view){
        int id = view.getId();

        if (id == R.id.btnGuardar)onClickBtnGuardar();

    }

    private void initComponents(){
        binding.btnGuardar.setOnClickListener(this);
        setCharola();
    }

    private void setCharola() {
        adapter = new RvCharolaAdapter(MockData.getCharola());

        LinearLayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        binding.rvCharola.setLayoutManager(manager);
        binding.rvCharola.setAdapter(adapter);

    }

    @SuppressLint("NotifyDataSetChanged")
    private void onClickBtnGuardar() {
        MockData.updateCharolas(adapter.getData());
        setCharola();
    }

}