package com.geomanage.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.geomanage.entities.Usuario;
import com.geomanage.entities.Endereco;
import com.geomanage.entities.Cidade;
import com.geomanage.dao.CidadeDao;
import com.geomanage.dao.UsuarioDao;
import com.geomanage.dao.EnderecoDao;

@Database(entities = {Usuario.class, Cidade.class, Endereco.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"ControleDeEnderecos")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract UsuarioDao usuarioDao();
    public abstract CidadeDao cidadeDao();
    public abstract EnderecoDao enderecoDao();
}