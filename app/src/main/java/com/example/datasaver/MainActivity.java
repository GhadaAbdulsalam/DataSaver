package com.example.datasaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import  android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
        TextView txt_pathShow;
        Button btn_openfile;
        Button button2;
        Intent myFileIntent;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button yourButton = (Button) findViewById(R.id.button2);
            yourButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSecondActivity(); }
            });

            txt_pathShow=(TextView) findViewById(R.id.txt_path);
            btn_openfile = (Button) findViewById(R.id.btn_openfile);
            button2=(Button) findViewById(R.id.button2);

            btn_openfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                    myFileIntent.setType("*/*");
                    startActivityForResult(myFileIntent,10);
                }
            });
        }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            switch (requestCode){

                case 10:
                    if(resultCode==RESULT_OK){
                        String path= data.getData().getPath();
                        txt_pathShow.setText(path);
                        button2.setEnabled(true);
                    }
                    break;
            }
        }

        public void xorFile(View view) throws IOException {
            String path= (String) txt_pathShow.getText();
            File src= new File(path);
            FileInputStream is = new FileInputStream(src);
            File dest=new File(path);
            FileOutputStream os = new FileOutputStream(dest);
            byte key[] ={1,2,3,4};
            byte[] data = new byte[1024]; //1 KB buffer
            int read = is.read(data), index = 0;
            while( read != -1 ) {
                for( int k=0; k<read; k++ ) {
                    data[k] ^= key[index % key.length];
                    index++;
                }
                os.write(data,0,read);
                read = is.read(data);
            }

            os.flush();
            os.close();
            is.close();
        }

        private void goToSecondActivity() { Intent intent = new Intent(this,
                Browse.class); startActivity(intent);

        }


    }
