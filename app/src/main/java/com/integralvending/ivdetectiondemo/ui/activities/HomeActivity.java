package com.integralvending.ivdetectiondemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.databinding.ActivityHomeBinding;
import com.integralvending.ivdetectiondemo.ui.adapters.RvArticulosAdapter;
import com.integralvending.ivdetectiondemo.ui.data.MockData;
import com.integralvending.ivdetectiondemo.ui.utils.USwal;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnEscanear) onClickBtnEscanear();
    }

    private void initComponents() {
        binding.btnEscanear.setOnClickListener(this);
        setMockData();
    }

    private void setMockData() {
        RvArticulosAdapter adapter = new RvArticulosAdapter(MockData.getMockData());

        LinearLayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );

        binding.rvArticulos.setLayoutManager(manager);
        binding.rvArticulos.setAdapter(adapter);
    }

    private void onClickBtnEscanear() {
        USwal.alertaOk(
                "Acción en construcción",
                "Funcionalidad aún no disponible",
                SweetAlertDialog.WARNING_TYPE,
                this
        );
    }
}