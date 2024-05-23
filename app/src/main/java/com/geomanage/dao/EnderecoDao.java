package com.geomanage.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geomanage.entities.Endereco;

import java.util.List;

@Dao
public interface EnderecoDao {
    @Insert
    void insert(Endereco endereco);

    @Update
    void update(Endereco endereco);

    @Delete
    void delete(Endereco endereco);

    @Query("SELECT * FROM Endereco WHERE cidadeId = :cidadeId")
    List<Endereco> getEnderecosByCidadeId(int cidadeId);

    @Query("SELECT * FROM Endereco")
    List<Endereco> getAllEnderecos();

    @Query("SELECT * FROM Endereco WHERE enderecoId = :enderecoId")
    Endereco getEnderecoById(int enderecoId);
}

