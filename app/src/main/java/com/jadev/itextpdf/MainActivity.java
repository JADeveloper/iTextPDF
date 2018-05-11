package com.jadev.itextpdf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etCorpo;
    private EditText etAutor;
    String folder_main = "PDFGerados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etTitulo = (EditText) findViewById(R.id.titulo);
        etCorpo = (EditText) findViewById(R.id.corpo);
        etAutor = (EditText) findViewById(R.id.autor);

        criarDirs();
    }

    public void gerarPDf(View v) {

        String titulo = etTitulo.getText().toString();
        String corpo = etCorpo.getText().toString();
        String autor = etAutor.getText().toString();

        final String filename = titulo.replace(" ", "").trim()+".pdf";
        File path = new File(Environment.getExternalStorageDirectory(), "PDFGerados");
        File pdffile = new File(path, filename);

        try{

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(pdffile));
            doc.open();

            Font p = new Font(Font.FontFamily.COURIER, 20, Font.NORMAL, BaseColor.BLACK);
            Font minhaFonte = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);

            Paragraph docTitulo = new Paragraph(titulo.toUpperCase(), minhaFonte);
            Paragraph docCorpo = new Paragraph(corpo);

            //Image img = Image.getInstance("");
            //img.scaleAbsolute(30, 30);

            doc.add(docTitulo);
            doc.add(docCorpo);
            doc.addAuthor(autor);
            doc.close();

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("O documento foi criado com sucesso, deseja abrir?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    File file = new File(Environment.getExternalStorageDirectory().getPath()+"/PDFGerados/"+filename);

                    Uri caminho = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(caminho, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            });
            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void criarDirs(){
        File dir = new File(Environment.getExternalStorageDirectory(), folder_main);
        if(!dir.exists()){
            dir.mkdir();
        }
    }
}
