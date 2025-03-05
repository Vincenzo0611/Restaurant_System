package org.example;

import java.io.Serializable;

public class Produkt_z_menu implements Serializable {
    private String nazwa;
    private int id;
    private int kod_z_kasy;
    private int section_display_order;
    private int display_order;

    public Produkt_z_menu(String nazwa, int id, int kod_z_kasy, int display_order, int section_display_order) {
        this.nazwa = nazwa;
        this.id = id;
        this.kod_z_kasy = kod_z_kasy;
        this.section_display_order = section_display_order;
        this.display_order = display_order;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getId()
    {

        return id;
    }

    public int getKod_z_kasy()
    {
        return kod_z_kasy;
    }

    public int getDisplay_order()
    {
        return display_order;
    }

    public int getSection_display_order() {
        return section_display_order;
    }
}
