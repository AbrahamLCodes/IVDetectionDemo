package com.integralvending.ivdetectiondemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.databinding.ActivityHomeBinding;
import com.integralvending.ivdetectiondemo.ui.adapters.RvArticulosAdapter;
import com.integralvending.ivdetectiondemo.data.MockData;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMockData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnEscanear) onClickBtnEscanear(view.getContext());
        if (id== R.id.btnConfiguracion) onClickcBtnConfiguracion(view.getContext());

    }

    private void initComponents() {
        binding.btnEscanear.setOnClickListener(this);
        binding.btnConfiguracion.setOnClickListener(this);
        setMockData();
    }

    private void setMockData() {

        Log.wtf("asdasd", "SettingUp MockData");

        RvArticulosAdapter adapter = new RvArticulosAdapter(MockData.getMockData());

        LinearLayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );

        binding.rvArticulos.setLayoutManager(manager);
        binding.rvArticulos.setAdapter(adapter);
    }

    private void onClickBtnEscanear(Context context) {

        Intent intent = new Intent(context, CamaraActivity.class);
        context.startActivity(intent);

       // USwal.alertaOk("Acción en construcción", "Funcionalidad aún no disponible", SweetAlertDialog.WARNING_TYPE, this);
    }

    private void onClickcBtnConfiguracion (Context context) {
        Intent intent = new Intent(context, CharolaActivity.class);
        context.startActivity(intent);

    }
}