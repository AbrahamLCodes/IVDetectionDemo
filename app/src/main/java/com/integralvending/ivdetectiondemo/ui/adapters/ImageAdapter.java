package com.integralvending.ivdetectiondemo.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.data.MockData;
import com.integralvending.ivdetectiondemo.models.MArticulo;
import com.integralvending.ivdetectiondemo.models.MCharola;
import com.integralvending.ivdetectiondemo.ui.activities.FullScreenImageActivity;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final MockData instancia = new MockData();
    private final ArrayList<MCharola> charolas = instancia.getCharola();
    private final ArrayList<MArticulo> articulos = instancia.getMockData();
    private ArrayList<byte[]> imageBytesList;

    public ImageAdapter(ArrayList<byte[]> imageBytesList) {
        this.imageBytesList = imageBytesList;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        byte[] imageBytes = imageBytesList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


        //https://stackoverflow.com/questions/23202130/android-convert-byte-array-from-camera-api-to-color-mat-object-opencv

        //Con un código así se puede transformar de negativo a colores normales


        Mat orig = new Mat(bitmap.getHeight(),bitmap.getWidth(), CvType.CV_8UC1);
        Bitmap myBitmap32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(myBitmap32, orig);
        Imgproc.cvtColor(orig, orig, Imgproc.COLOR_BGR2RGB,4);




        holder.imageView.setImageBitmap(bitmap);

        int imageNumber = position + 1; // posicion

        // Comparacion con la base de datos

        int resultadoComparacion = -1;

        int numeroMCharola = -1;

        for (MCharola charola : charolas) {
            // Comparar el imageNumber con el idRectangulo de la charola
            if (charola.getIdRectangulo() == imageNumber) {
                numeroMCharola = charola.getNumero();
                break;
            }
        }

        Intent intent = new Intent(holder.itemView.getContext(), FullScreenImageActivity.class);

        if (numeroMCharola != -1) {
            intent.putExtra("NUMERO_MCHAROLA", numeroMCharola);
        }
        holder.textViewNumber.setText("La charola N°"+imageNumber + " contiene");

        // Lista de recyclerview

        // Agregar OnClickListener a la imagen
        holder.imageView.setOnClickListener(view -> {
            // Obtener la imagen en pantalla completa
            intent.putExtra("imageBytes", imageBytes);
            intent.putExtra("imageNumber", imageNumber); // Agregar el número como extra
            view.getContext().startActivity(intent);
        });



    }



    @Override
    public int getItemCount() {
        return imageBytesList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNumber, nombreprod;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            imageView = itemView.findViewById(R.id.imageView);
            nombreprod = itemView.findViewById(R.id.tvDescripcion);

        }
    }
}
