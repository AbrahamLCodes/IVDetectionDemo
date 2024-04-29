package com.integralvending.ivdetectiondemo.ui.activities;

import static org.tensorflow.lite.task.vision.detector.ObjectDetector.createFromFileAndOptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.detector.Detection;
import org.tensorflow.lite.task.vision.detector.ObjectDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class FullScreenImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final float MAX_FONT_SIZE = 96F;
    private ImageView inputImageView;
    private TextRecognizer textRecognizer;
    private TextView tvTextoReconocido, tvTextoIncorrecto;
    private MockData instancia = new MockData();
    private ArrayList<MArticulo> Articulos = instancia.getMockData();
    private ArrayList<MCharola> Charolas = instancia.getCharola();
    private int numCharolaRecibido = 0;
    private int contador = 0, contadorerroneo =0,RP=0,DP=0;
    private final List<Detection>results;

    public FullScreenImageActivity() {
        this.results = new ArrayList<>();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        inputImageView = findViewById(R.id.fullScreenImageView);
        tvTextoReconocido = findViewById(R.id.tvTextoReconocido);
        tvTextoIncorrecto = findViewById(R.id.tvTextoIncorrecto);

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);


        // Obtener datos del intent
        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");

        assert imageBytes != null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        inputImageView.setImageBitmap(bitmap);

        // Configurar la escala del ImageView
        inputImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Configurar el reconocedor de texto
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        try {
            // Ejecutar la detección de objetos automáticamente al abrir la actividad
            runObjectDetection(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        debugPrint(results);
    }

    private void runObjectDetection(Bitmap bitmap) throws IOException {
        if (bitmap != null) {
            TensorImage image = TensorImage.fromBitmap(bitmap);

            ObjectDetector.ObjectDetectorOptions options = ObjectDetector.ObjectDetectorOptions.builder()
                    .setMaxResults(10)
                    .setScoreThreshold(0.30f) // 0.45 funciona bien para los doritos y chettos Naranjas
                    .build();
            ObjectDetector detector = createFromFileAndOptions(
                    this,
                    "polboderozazzz.tflite",
                    options
            );

            // silletrained_model
            //bikinicodde
            //supinchemadre
            //Strained_modelnew
            // pincOpen.tflite
            //polboderozazzz tal vez este tambien
            //ondeado_model jala bien
            //Makinatrained_model     //

            // Realizar la detección de objetos
            List<Detection> results = detector.detect(image);

            // Procesar los resultados para mostrar en la imagen
            List<DetectionResult> resultToDisplay = new ArrayList<>();
            for (Detection detection : results) {
                if (detection.getCategories() != null && !detection.getCategories().isEmpty()) {
                    Category category = detection.getCategories().get(0);
                    String text = category.getLabel() + ", " + (int) (category.getScore() * 100) + "%";
                    resultToDisplay.add(new DetectionResult(detection.getBoundingBox(), text));
                }
            }

            // Dibujar los resultados en la imagen y mostrarla en el hilo de la interfaz de usuario
            Bitmap imgWithResult = drawDetectionResult(bitmap, resultToDisplay);
            runOnUiThread(() -> {
                inputImageView.setImageBitmap(imgWithResult);
                debugPrint(results);  // Llamar a debugPrint después de mostrar la imagen con resultados
            });
            // Liberar recursos del detector
            detector.close();
        } else {
            // Manejar el caso en que la imagen es nula
            Log.e("DEBUG_TAG", "La imagen es nula");
        }
    }

    @SuppressLint("SetTextI18n")
    private void debugPrint(List<Detection> results) {
        numCharolaRecibido = getIntent().getIntExtra("NUMERO_MCHAROLA", -1);
        // NumeroMCharola es el numero del producto en la charola
        // se hace la compracion que es el del numero del producto
        Log.d("Charola", "La charola recibida es " + numCharolaRecibido);

        String finalnombre = null, NombreIncorrecto = null;
        List<String> finalnombreincorrecto = new ArrayList<>();


        int FinalIdArticulo = 0;
        int NCharola =0;

        for (MCharola charola: Charolas){
            NCharola = charola.getIdRectangulo();


            for(MArticulo articulo: Articulos){
                Log.d("Articulos", "Los articulos son:" + articulo);
                if (articulo.getIdArticulo() == numCharolaRecibido) {
                    finalnombre = articulo.getNombre();
                    FinalIdArticulo = articulo.getIdArticulo();

                }
                else {
                    finalnombreincorrecto.add(articulo.getIdDetection());
                    Log.d("Nombreincorrecto", "Los nombres son incorrectos son " + finalnombreincorrecto);
                }
            }
        }

        String m = finalnombre ;

        // M es el nombre del producto y category tambien es donde
        // Se debe comparar tengo que guardar m
        // Croe que tengo mal la comparacion con la charola
        // Estoy mandando a llamar el id articulo

        if (results != null) {
            DP = 0;
            RP = 0;
            for (int i = 0; i < results.size(); i++) {
                Detection obj = results.get(i);
                List<Category> categories = obj.getCategories();
                if (categories != null) {
                    for (int j = 0; j < categories.size(); j++) {
                        Category category = categories.get(j);
                        Log.d("LARAZA", "category.getLabel(): " + category.getLabel());
                        Log.d("LARAZA", "m: " + m);
                        if (category.getLabel().trim().equals(m)) {
                            RP++;
                            runOnUiThread(() -> tvTextoReconocido.setText("El producto detectado es "+ m + " y la cantidad es " + RP+ " en la charola "+numCharolaRecibido));
                        } else {
                            DP++;
                            String nombreIncorrecto = category.getLabel().trim();
                            runOnUiThread(() -> {
                                tvTextoIncorrecto.setText("Producto incorrecto  " + nombreIncorrecto + ", Cantidad: " + DP );
                                // Imprimir el nombre y la cantidad del producto incorrecto
                                Log.d("ProductoIncorrecto", "Nombre: " + nombreIncorrecto + ", Cantidad: " + DP + ", Charola: ");
                            });
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnGuardar) onClickbtnGuardar();
    }

    private void onClickbtnGuardar() {
        // Encontrar el index del articulo en el ArrayList de MockData
        int index = 0;

        MArticulo mArticulo = null;
        for (int i = 0; i < MockData.getMockData().size(); i++) {
            mArticulo = MockData.getMockData().get(i);
            if (mArticulo.getIdArticulo() == numCharolaRecibido) {
                index = i;
                break;
            }
        }

        //Actualizar cantidad del articulo
        mArticulo.setCantidad(RP);

        // Actualizar articulo en MockData
        ArrayList<MArticulo> currentMockData = MockData.getMockData();
        currentMockData.set(index, mArticulo);

        MockData.updateMockData(currentMockData);

        finish();
    }


    private Bitmap drawDetectionResult(Bitmap bitmap, List<DetectionResult> detectionResults) {
        Bitmap outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outputBitmap);
        Paint pen = new Paint();
        pen.setTextAlign(Paint.Align.LEFT);

        for (DetectionResult result : detectionResults) {
            // Dibujar el cuadro delimitador
            pen.setColor(Color.RED);
            pen.setStrokeWidth(8F);
            pen.setStyle(Paint.Style.STROKE);
            RectF box = result.getBoundingBox();
            canvas.drawRect(box, pen);

            Rect tagSize = new Rect(0, 0, 0, 0);

            // Calcular el tamaño correcto de la fuente
            pen.setStyle(Paint.Style.FILL_AND_STROKE);
            pen.setColor(Color.YELLOW);
            pen.setStrokeWidth(2F);

            pen.setTextSize(MAX_FONT_SIZE);
            pen.getTextBounds(result.getText(), 0, result.getText().length(), tagSize);
            float fontSize = pen.getTextSize() * box.width() / tagSize.width();

            // Ajustar el tamaño de la fuente para que el texto esté dentro del cuadro delimitador
            if (fontSize < pen.getTextSize()) pen.setTextSize(fontSize);

            float margin = (box.width() - tagSize.width()) / 2.0F;
            if (margin < 0F) margin = 0F;
            canvas.drawText(
                    result.getText(), box.left + margin,
                    box.top + tagSize.height() * 1F, pen
            );
        }
        return outputBitmap;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}

class DetectionResult {
    private final RectF boundingBox;
    private final String text;

    public DetectionResult(RectF boundingBox, String text) {
        this.boundingBox = boundingBox;
        this.text = text;
    }

    public RectF getBoundingBox() {
        return boundingBox;
    }

    public String getText() {
        return text;
    }
}