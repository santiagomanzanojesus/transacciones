package com.company.app.enums;

public enum Estatus {
    APROBADA("Aprobada"),
    CANCELADA("Cancelada"),
    PENDIENTE("Pendiente"),
    OTRO("Otro");

    private final String estatus;

    Estatus(String estatus){
        this.estatus = estatus;
    }
    public  String getEstatus(){
        return estatus;
    }

}
