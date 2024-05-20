package com.geomanage.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cidade {
    @PrimaryKey(autoGenerate = true)
    public int cidadeId;

    public String cidade;

    public String estado;
}

