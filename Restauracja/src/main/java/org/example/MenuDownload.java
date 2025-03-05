package org.example;

import java.util.*;
import java.sql.*;

public class MenuDownload {

    private List<Dzial_menu> menu = new ArrayList<>();

    public List<Dzial_menu> getMenu() {
        return menu;
    }

    public boolean downloadMenu()
    {
        String url = "db_url";
        String user = "db_user";
        String password = "db_password";

        String query = "SELECT * FROM menu_sections";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet menu_sections = stmt.executeQuery(query)) {
            while (menu_sections.next()) { // Przechodzimy przez wyniki
                byte[] binaryData = menu_sections.getBytes("id");
                UUID id = bytesToUuid(binaryData);
                String name = menu_sections.getString("name");
                int display_order = menu_sections.getInt("display_order");

                System.out.println("ID: " + id + ", Name: " + name + ", display order: " + display_order);

                menu.add(new Dzial_menu(id, name, display_order));
            }
            menu.sort(Comparator.comparingInt(Dzial_menu::getDisplay_order));

        } catch (SQLException e) {
            System.out.println("Błąd SQL: " + e.getMessage());
            return false;
        }
        query = "SELECT * FROM menu_elements";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet menu_sections = stmt.executeQuery(query)) {
            int id_iterator = 1;
            while (menu_sections.next()) { // Przechodzimy przez wyniki
                int id = id_iterator;
                id_iterator++;
                byte [] binaryData = menu_sections.getBytes("menu_section_id");
                UUID section_id = bytesToUuid(binaryData);
                String name = menu_sections.getString("name");
                int code = menu_sections.getInt("code");
                int display_order = menu_sections.getInt("display_order");

                System.out.println("ID: " + id + ", Name: " + name + ", code: " + code + ", section_id: " + section_id);

                Optional<Dzial_menu> dzialMenu = menu.stream()
                        .filter(dzial -> dzial.getId().equals(section_id))
                        .findFirst();
                // Sprawdzenie, czy znaleziono dział menu
                if (dzialMenu.isPresent()) {
                    Dzial_menu znalezionyDzial = dzialMenu.get();
                    znalezionyDzial.produkty.add(new Produkt_z_menu(name, id, code,display_order, znalezionyDzial.getDisplay_order()));
                } else {
                    System.out.println("Dział menu o podanym id nie został znaleziony.");
                    return false;
                }

            }
            menu.sort(Comparator.comparingInt(Dzial_menu::getDisplay_order));
            for(Dzial_menu dzial_menu : menu)
            {
                dzial_menu.produkty.sort(Comparator.comparingInt(Produkt_z_menu::getDisplay_order));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Błąd SQL: " + e.getMessage());
            return false;
        }
    }

    private static UUID bytesToUuid(byte[] bytes) {
        long mostSigBits = 0;
        long leastSigBits = 0;

        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xff);
            leastSigBits = (leastSigBits << 8) | (bytes[8 + i] & 0xff);
        }

        return new UUID(mostSigBits, leastSigBits);
    }



}
