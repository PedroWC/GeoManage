package com.geomanage.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geomanage.entities.Cidade;

import java.util.List;

@Dao
public interface CidadeDao {
    @Insert
    void insert(Cidade cidade);

    @Update
    void update(Cidade cidade);

    @Delete
    void delete(Cidade cidade);

    @Query("SELECT * FROM Cidade")
    List<Cidade> getAllCidades();
}

