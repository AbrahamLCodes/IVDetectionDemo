package com.integralvending.ivdetectiondemo.ui.adapters;

import static android.content.Intent.getIntent;

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
import java.util.jar.Attributes;

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

        Mat orig = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC1);
        Bitmap myBitmap32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(myBitmap32, orig);
        Imgproc.cvtColor(orig, orig, Imgproc.COLOR_BGR2RGB, 4);
        holder.imageView.setImageBitmap(bitmap);

        int imageNumber = position + 1;
        int numeroMCharola = -1;
        String nombre = null;
        int total = 0, idArt = 0, idRec = 0;

        for (MCharola charola : charolas) {
            idRec = charola.getIdRectangulo();
            if (charola.getIdRectangulo() == imageNumber) {
                numeroMCharola = charola.getNumero();

                for (MArticulo articulo : articulos) {
                    idArt = articulo.getIdArticulo();
                    nombre = articulo.getNombre();
                    total = articulo.getCantidad();

                    // Agregar condición para verificar si el número de MCharola coincide con el ID de Articulo
                    if (numeroMCharola == idArt) {
                        Log.d("Mala", "El numero de producto " + numeroMCharola);
                        Log.d("Mala", "El producto es" + nombre + " y el total es de" + total);
                        holder.nombreprod.setText(nombre + " y el total es de :" + total);
                    }
                }
            }
        }

        Intent intent = new Intent(holder.itemView.getContext(), FullScreenImageActivity.class);
        if (numeroMCharola != -1) {
            Log.d("Mala", "El numero enviado es " + numeroMCharola);
            intent.putExtra("NUMERO_MCHAROLA", numeroMCharola);
        }

        holder.textViewNumber.setText("La charola N°" + imageNumber + " contiene");

        // Agregar OnClickListener a la imagen
        holder.imageView.setOnClickListener(view -> {
            // Obtener la imagen en pantalla completa
            intent.putExtra("imageBytes", imageBytes);
            intent.putExtra("imageNumber", imageNumber);
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
