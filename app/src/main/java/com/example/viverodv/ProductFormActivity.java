package com.example.viverodv;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ProductFormActivity extends AppCompatActivity {

    // Campos/Elementos
    private EditText editTextPlantName;
    private AutoCompleteTextView editTextPlantType;
    private EditText editTextPlantPrice;
    private Button buttonAddProduct;
    private LinearLayout productsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular java con XML
        editTextPlantName = findViewById(R.id.editTextPlantName);
        editTextPlantType = findViewById(R.id.editTextPlantType);
        editTextPlantPrice = findViewById(R.id.editTextPlantPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        productsContainer = findViewById(R.id.products_container);
        // Configurar opciones del dropdown
        String[] tipos = {"Planta de interior", "Planta de exterior", "Insumo", "Herramienta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tipos);
        editTextPlantType.setAdapter(adapter);
        // Deshabilita teclado
        editTextPlantType.setInputType(0);
        // Forzar apertura del dropdown al hacer click
        editTextPlantType.setFocusable(false);
        // Listener del dropdown
        editTextPlantType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPlantType.showDropDown();
            }
        });
        // Listener del boton
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    // Validar y cargar a la lista
    private void addProduct() {
        // Obtener data de los campos
        String name = editTextPlantName.getText().toString().trim();
        String type = editTextPlantType.getText().toString().trim();
        String price = editTextPlantPrice.getText().toString().trim();
        // Log.e("MainActivity", "Nombre: " + name + ", Tipo: " + type + ", Precio: " + price);

        // Verificar si estan vacios
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(price)) {
            // Log.e("MainActivity", "Error: Campos vacíos");
            Toast.makeText(this, R.string.mje_error_campo_vacio, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Agregar la tarjeta del producto a la lista
        addProductCard(name, type, price);

        // Limpiar los campos y enfocar el primero
        editTextPlantName.setText("");
        editTextPlantType.setText("");
        editTextPlantPrice.setText("");
        editTextPlantName.requestFocus();
    }

    // Crear la tarjeta del producto
    private void addProductCard(String name, String type, String price) {
        // Crea un TextView
        TextView productTextView = new TextView(this);
        // Formatear la representacion de los datos del producto
        String productInfo = getString(R.string.formato_txt_tarjeta, name, type, price);
        // Insertar el texto en el TextView
        productTextView.setText(productInfo);
        // Dar formato a la tarjeta: fondo, color, alineacion
        productTextView.setBackground(ContextCompat.getDrawable(this, R.drawable.card_background));
        productTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
        productTextView.setGravity(Gravity.CENTER_VERTICAL);
        
        // Configurar objeto de parametros de layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = (int) getResources().getDimension(R.dimen.card_margin);
        params.setMargins(0, 0, 0, margin);
        // Setear los parametros a la tarjeta
        productTextView.setLayoutParams(params);
        // Configurar el padding
        int padding = (int) getResources().getDimension(R.dimen.card_padding);
        productTextView.setPadding(padding, padding, padding, padding);
        // Agregar la tarjeta al inicio del contenedor
        productsContainer.addView(productTextView, 0);
    }
}