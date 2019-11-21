package com.example.cliente.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;


@Data
@Document(collection="cliente")
public class Cliente implements Serializable {
    @Id
    @NotNull
    private String id;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rut;
    private int edad;
    private String sexo;
    private String direccion;
    private String telefono;
    private String tipoLicencia;
    private Date fechaEmisionLicencia;
    private Date fechaVencimientoLicencia;

}
