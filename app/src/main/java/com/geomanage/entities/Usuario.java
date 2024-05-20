package com.geomanage.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int usuarioId;

    public String nome;

    public String email;

    public String senha;


}
