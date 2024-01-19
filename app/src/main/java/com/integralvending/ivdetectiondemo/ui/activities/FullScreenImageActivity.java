package com.integralvending.ivdetectiondemo.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.integralvending.ivdetectiondemo.R;
import com.integralvending.ivdetectiondemo.data.MockData;
import com.integralvending.ivdetectiondemo.models.MArticulo;
import com.integralvending.ivdetectiondemo.models.MCharola;
import com.integralvending.ivdetectiondemo.ui.adapters.ImageAdapter;
import com.integralvending.ivdetectiondemo.ui.adapters.RvArticulosAdapter;
import com.integralvending.ivdetectiondemo.utils.USwal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class FullScreenImageActivity extends AppCompatActivity {

    private ImageView inputImageView;
    private TextRecognizer textRecognizer;
    private TextView tvTextoReconocido, tvTextoIncorrecto;
    private MockData  instancia = new MockData();
    private ArrayList<MArticulo> Articulos = instancia.getMockData();
    private ArrayList<MCharola> Charolas = instancia.getCharola();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        inputImageView = findViewById(R.id.fullScreenImageView);
        tvTextoReconocido = findViewById(R.id.tvTextoReconocido);
        tvTextoIncorrecto = findViewById(R.id.tvTextoIncorrecto);


        // Obtener datos del intent
        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");

        assert imageBytes != null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        inputImageView.setImageBitmap(bitmap);

        // Configurar la escala del ImageView
        inputImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Configurar el reconocedor de texto
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        reconocerTextoDeImagen(bitmap);
    }


    private void reconocerTextoDeImagen(Bitmap bitmap) {
        int numeroMCharolaRecibido = getIntent().getIntExtra("NUMERO_MCHAROLA", -1);
        // NumeroMCharola es el numero del producto en la charola
        // se hace la compracion que es el del numero del producto
        Log.d("Charola", "La charola recibida es " + numeroMCharolaRecibido);

        String finalcodigo = null;
        String finalnombre = null, NombreIncorrecto = null;
        List<String> finalcodigoIncorrecto = new ArrayList<>();

        int FinalNumeroCharola = 0;
        int FinalNumeroArt = 0;
        int NCharola =0;
        int FinalIdArticulo=0;

        for (MCharola charola : Charolas){
            Log.d("Charolas","Las charolas son: "+ charola);
            FinalNumeroCharola = charola.getIdRectangulo();
            FinalNumeroArt = charola.getNumero();

            for (MArticulo articulo : Articulos) {

                Log.d("Articulos", "Los articulos son:" + articulo);
                if (articulo.getIdArticulo() == numeroMCharolaRecibido) {
                    finalcodigo = articulo.getIdDetection();
                    finalnombre = articulo.getNombre();
                    FinalIdArticulo = articulo.getIdArticulo();
                }
                else {
                    finalcodigoIncorrecto.add(articulo.getIdDetection());
                    Log.d("Nuemroscorrectos","Los codigos incorrectos son "+finalcodigoIncorrecto);
                }
            }

            // Esto lo estoy utilizando para tener el valor de cada articulo con su respectiva
            // Charola
            if (FinalNumeroArt == FinalIdArticulo) {
                 NCharola = FinalNumeroCharola;
                Log.d("Numero de charola","El numero de la charola es el "+FinalNumeroCharola);
            }
        }

        // Crear una instancia de InputImage desde el bitmap
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

        final String codigo = finalcodigo;
        final List<String> CodigoErroneo = finalcodigoIncorrecto;
        final String nombre = finalnombre;
        final int NumeroCharola = NCharola;


        // Procesar la imagen y reconocer el texto
        textRecognizer.process(inputImage)
                .addOnSuccessListener(text -> {
                    // Manipular el texto reconocido aquí
                    String textoReconocido = text.getText();

                    // Inicializar contador de palabras que cumplen con los criterios
                    int contadorPalabras = 0,  CharolaArtIncorrecto=0;
                    int contadorIncorrecto = 0;

                    // Dividir el texto en palabras usando un espacio como separador
                    String[] palabras = textoReconocido.split("\\s+");

                    String nombreerroneo = null;
                    int i = 0, Ci = 0, Chi=0;

                    String producto = null;
                    for (String palabra : palabras) {
                        if (palabra.contains(codigo)) {
                            Log.d("PalabraConCodigo", palabra);

                            // Incrementar el contador de palabras
                            contadorPalabras++;
                        }
                            if (!palabra.contains(codigo)) {
                                producto = palabra;
                                contadorIncorrecto++;
                                for (MCharola charola : Charolas){
                                    Ci = charola.getNumero();
                                    Chi = charola.getIdRectangulo();

                                    for (MArticulo articulo : Articulos) {
                                        if (Objects.equals(articulo.getIdDetection(), producto)) {
                                            nombreerroneo = articulo.getNombre();
                                            i = articulo.getIdArticulo();
                                        }
                                    }

                                    if (i == Ci) {
                                        CharolaArtIncorrecto = Chi;
                                    }

                                }
                                final int NcharolaArtIncorrecto = CharolaArtIncorrecto;
                                final String nombreErr = nombreerroneo;
                                if (nombreErr != null){
                                    Log.d("PalabraINC", "Se detecto un producto incorrecto en esta charola " + nombreErr + " y pertemece a la charola  " + NcharolaArtIncorrecto);

                                    // Construir el mensaje a mostrar en el TextView
                                    String mensaje = "Se detectó un producto incorrecto en esta charola " + nombreErr +
                                            " y pertenece a la charola " + NcharolaArtIncorrecto+".";

                                    // Obtener el texto actual del TextView (si hay alguno)
                                    String textoActual = tvTextoIncorrecto.getText().toString();

                                    // Agregar el nuevo mensaje al texto existente (puedes ajustar el formato según tus necesidades)
                                    String nuevoTexto = textoActual + "\n" + mensaje;

                                    tvTextoIncorrecto.setText(nuevoTexto);
                                }



                            }
                    }

                    if (contadorPalabras >= 1){
                        Log.d("productos", "El producto es " + nombre + " y el total es de " + contadorPalabras);

                        tvTextoReconocido.setText("El producto es " + nombre + " y el total es de " + contadorPalabras + " en la charola " + NumeroCharola+".");


                    }else {
                        USwal.alertaOk("Error al reconocer", "No se detecto ningun producto en la charola vuelva a tomar la foto.", SweetAlertDialog.WARNING_TYPE, this);
                    }

                    // Crear un nuevo Bitmap con el texto superpuesto
                    Bitmap bitmapWithText = drawTextOnBitmap(bitmap, textoReconocido);


                    // Establecer el nuevo Bitmap en el ImageView
                    inputImageView.setImageBitmap(bitmapWithText);
                })
                .addOnFailureListener(e -> {
                    // Manejar errores
                    Toast.makeText(FullScreenImageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private Bitmap drawTextOnBitmap(Bitmap originalBitmap, String text) {
        // Copiar el bitmap original para evitar cambios permanentes
        Bitmap bitmapCopy = originalBitmap.copy(originalBitmap.getConfig(), true);

        // Crear un lienzo para dibujar sobre el bitmap copiado
        Canvas canvas = new Canvas(bitmapCopy);

        // Configurar el pincel para el texto
        Paint paint = new Paint();
        paint.setColor(Color.RED);  // Color del texto
        paint.setTextSize(30);      // Tamaño del texto

        // Calcular la posición para centrar el texto
        float x = (canvas.getWidth() - paint.measureText(text)) / 2;
        float y = canvas.getHeight() - 50;  // Altura desde la parte inferior

        // Dibujar el texto en el lienzo
        canvas.drawText(text, x, y, paint);

        return bitmapCopy;
    }
}