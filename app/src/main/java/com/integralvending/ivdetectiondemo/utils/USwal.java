package com.integralvending.ivdetectiondemo.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class USwal {

    public static SweetAlertDialog progress(String titulo, String mensaje, Context context) {
        SweetAlertDialog progressSwal = new SweetAlertDialog(context);
        progressSwal.setTitleText(titulo);
        progressSwal.setContentText(mensaje);
        progressSwal.setContentTextSize(25);
        progressSwal.setCancelable(false);
        progressSwal.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        return progressSwal;
    }

    public static void alertaOk(String titulo, String mensaje, int tipoAlerta, Context context) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context);
        sweetAlertDialog.setTitle(titulo);
        sweetAlertDialog.setContentText(mensaje);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.changeAlertType(tipoAlerta);
        sweetAlertDialog.setContentTextSize(16);
        sweetAlertDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        sweetAlertDialog.show();
    }

}
