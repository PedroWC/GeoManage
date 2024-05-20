package com.geomanage.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Cidade.class,
        parentColumns = "cidadeId",
        childColumns = "cidadeId",
        onDelete = ForeignKey.CASCADE))
public class Endereco {
    @PrimaryKey(autoGenerate = true)
    public int enderecoId;

    public String descricao;

    public double latitude;

    public double longitude;

    public int cidadeId;
}
