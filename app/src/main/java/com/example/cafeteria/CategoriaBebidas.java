package com.example.cafeteria;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CategoriaBebidas extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listaBebidas = getListView();

        try {

            SQLiteOpenHelper bebidaBDHelper =
                    new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();

            // cria o cursor
            cursor = db.query("BEBIDA", new String[]{"_id", "NOME"},
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NOME"},
                    new int[]{android.R.id.text1},
                    0);

            listaBebidas.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override // fechar o cursor e BD
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override // quando um item da lista é clicado
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(CategoriaBebidas.this,TelaBebida.class);
        Bundle params = new Bundle();
        params.putInt("id", (int)id);
        intent.putExtras(params);
        startActivity(intent);
    }// fim do metodo
}