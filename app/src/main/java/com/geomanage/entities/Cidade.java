package com.geomanage.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cidade {
    @PrimaryKey(autoGenerate = true)
    public int cidadeId;


    public String cidade;

    public String estado;

    public int getCidadeId() { return cidadeId; }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return cidade + ", " + estado;
    }
}

