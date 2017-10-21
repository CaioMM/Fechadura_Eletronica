package caio.controlefechadura;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ListaDispositivos extends ListActivity {

    private BluetoothAdapter bluetoothDispositivo = null;

    static String endereco_MAC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        bluetoothDispositivo = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> dispositivosPareados = bluetoothDispositivo.getBondedDevices();

        if(dispositivosPareados.size()>0){
            for(BluetoothDevice dispositivo : dispositivosPareados){
                String nomeBt = dispositivo.getName();
                String macBt = dispositivo.getAddress();
                ArrayBt.add(nomeBt + "\n" + macBt);
            }
        }
        setListAdapter(ArrayBt);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();

        //Toast.makeText(getApplicationContext(), "Info: " + informacaoGeral, Toast.LENGTH_LONG).show();
        String enderecoMAC =  informacaoGeral.substring(informacaoGeral.length() - 17);

        //Toast.makeText(getApplicationContext(), "MAC: " + enderecoMAC, Toast.LENGTH_LONG).show();

        Intent retornaMAC = new Intent();
        retornaMAC.putExtra(endereco_MAC, enderecoMAC);
        setResult(RESULT_OK, retornaMAC);
        finish();

    }
}
