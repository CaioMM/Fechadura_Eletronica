package caio.controlefechadura;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Controle extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8,
            button9, button0, buttonMs, buttonFecharPorta,Ok;
    ImageButton ButtonBt;

    private OutputStream outputStream;
    boolean conexao = false;
    private static final int solicitaAtivacaoBt = 1;
    private static final int solicitaConexaoBt = 2;
    private static String MAC = null;

    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothAdapter bluetoothDispositivo = null;
    BluetoothDevice meuDispositivo = null;
    BluetoothSocket meuSocket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle);

        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        buttonMs = (Button)findViewById(R.id.buttonMs);
        buttonFecharPorta = (Button)findViewById(R.id.buttonFecharPorta);
        ButtonBt = (ImageButton)findViewById(R.id.imageButtonBt);
        Ok = (Button)findViewById(R.id.Ok);


        bluetoothDispositivo = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothDispositivo == null){
            //verifica se o dispositivo possui bluetooth
            Toast.makeText(getApplicationContext(), "Device does not support Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }else if(!bluetoothDispositivo.isEnabled()){
            //verifica se o bluetooth está ativado
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, solicitaAtivacaoBt);
        }

        ButtonBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conexao){
                    //desconectar
                    try{
                        meuSocket.close();
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Conexão Encerrada", Toast.LENGTH_LONG).show();

                    }   catch (IOException erro){
                        Toast.makeText(getApplicationContext(), "Ocorreu um Erro " + erro, Toast.LENGTH_LONG).show();

                    }

                } else {
                    //conectar
                    Intent abreLista = new Intent(Controle.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, solicitaConexaoBt);

                }
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("0".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("1".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("2".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("3".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("4".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("5".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("6".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("7".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("8".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("9".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        buttonFecharPorta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conexao) {
                    try {
                        outputStream.write("f".getBytes());
                    } catch (IOException erro) {
                        erro.printStackTrace();
                    }
                }
            }
        });

        buttonMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("m".getBytes());
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    try{
                        outputStream.write("o".getBytes());
                        Toast.makeText(getApplicationContext(), "ok enviado", Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        erro.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case solicitaAtivacaoBt:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth Ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth Desativado", Toast.LENGTH_LONG).show();
                    finish();
                }
            break;

            case solicitaConexaoBt:
                if(resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(ListaDispositivos.endereco_MAC);

                    //Toast.makeText(getApplicationContext(), "MAC: " + MAC, Toast.LENGTH_LONG).show();
                    meuDispositivo = bluetoothDispositivo.getRemoteDevice(MAC);
                    try{
                        meuSocket = meuDispositivo.createRfcommSocketToServiceRecord(MEU_UUID);
                        meuSocket.connect();
                        outputStream = meuSocket.getOutputStream();
                        conexao = true;
                        Toast.makeText(getApplicationContext(), "Conectado com MAC: " + MAC, Toast.LENGTH_LONG).show();

                    }   catch (IOException erro){
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Ocorreu um Erro " + erro, Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Falha ao Obter o Endereço MAC", Toast.LENGTH_LONG).show();
                }
        }
    }
}
