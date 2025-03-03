package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Dzial_menu {
    private UUID id;
    private String nazwa;
    private int display_order;

    List<Produkt_z_menu> produkty = new ArrayList<>();

    public Dzial_menu(UUID id,String nazwa, int order)
    {
        this.nazwa = nazwa;
        this.id = id;
        this.display_order = order;
    }
    public String getNazwa() {
        return nazwa;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public UUID getId() {
        return id;
    }
}
