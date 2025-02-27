package org.example;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Collections;


public class Wysylka {
    // Tworzenie synchronizowanych list
    List<ObjectOutputStream> monitory = Collections.synchronizedList(new ArrayList<>());
    List<ObjectInputStream> monitory_in = Collections.synchronizedList(new ArrayList<>());

    public boolean testing = false;

    private int id_start_napoi;

    public void setId_start_napoi(int id_start_napoi) {
        this.id_start_napoi = id_start_napoi;
    }

    public int getId_start_napoi() {
        return id_start_napoi;
    }

    private ScheduledExecutorService scheduler;

    public Wysylka() {
        // Tworzymy executor, który będzie regularnie wywoływał metodę test
        scheduler = Executors.newScheduledThreadPool(1);
        testing = false;
        // Jeden wątek do cyklicznego zadania
        //startTestScheduler();  // Uruchamiamy scheduler
    }

    public void test()
    {
        synchronized (monitory) {
            if(monitory.isEmpty())
            {
                return;
            }
        }

        synchronized (monitory) {
            synchronized (monitory_in) {
                Iterator<ObjectOutputStream> iterator = monitory.iterator();
                Iterator<ObjectInputStream> iterator_in = monitory_in.iterator();

                while (iterator.hasNext()) {
                    ObjectOutputStream oos = iterator.next();
                    ObjectInputStream in = iterator_in.next();

                    try {
                        if (oos != null) {
                            // Wysyłanie wiadomości "ping"
                            oos.writeUTF("ping");
                            oos.flush();


                            String response = in.readUTF();
                            if ("pong".equals(response)) {
                                System.out.println("Odebrano pong od klienta");
                            } else {
                                throw new IOException("Niepoprawna odpowiedź od klienta.");
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        // Brak odpowiedzi od klienta w określonym czasie
                        System.out.println("Timeout: brak odpowiedzi pong od klienta. Usuwam klienta.");
                        iterator.remove();  // Usuwamy klienta z listy
                        iterator_in.remove();  // Usuwamy klienta z listy
                    } catch (IOException e) {
                        // Jeśli połączenie jest rozłączone
                        System.out.println("Połączenie zostało rozłączone. Usuwam strumień.");
                        iterator.remove();  // Usuwamy bezpiecznie z listy
                        iterator_in.remove();  // Usuwamy bezpiecznie z listy
                    }
                }
            }
        }
    }

    public void test_print()
    {
        synchronized (monitory) {
            if (monitory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Brak połączonych klientów.");
                return;
            }
        }

        synchronized (monitory) {
            synchronized (monitory_in) {
                Iterator<ObjectOutputStream> iterator = monitory.iterator();
                Iterator<ObjectInputStream> iterator_in = monitory_in.iterator();

                while (iterator.hasNext()) {
                    ObjectOutputStream oos = iterator.next();
                    ObjectInputStream in = iterator_in.next();

                    try {
                        if (oos != null) {
                            // Wysyłanie wiadomości "ping"
                            oos.writeUTF("ping");
                            oos.flush();

                            String response = in.readUTF();
                            if ("pong".equals(response)) {
                                JOptionPane.showMessageDialog(null, "Odebrano pong od klienta");
                            } else {
                                throw new IOException("Niepoprawna odpowiedź od klienta.");
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        // Brak odpowiedzi od klienta w określonym czasie
                        JOptionPane.showMessageDialog(null, "Timeout: brak odpowiedzi pong od klienta. Usuwam klienta.");
                        iterator.remove();  // Usuwamy klienta z listy
                        iterator_in.remove();  // Usuwamy klienta z listy
                    } catch (IOException e) {
                        // Jeśli połączenie jest rozłączone
                        JOptionPane.showMessageDialog(null, "Połączenie zostało rozłączone. Usuwam strumień.");
                        iterator.remove();  // Usuwamy bezpiecznie z listy
                        iterator_in.remove();  // Usuwamy bezpiecznie z listy
                    }
                }
            }
        }
    }


    public void ADD_PARAGON(Paragon aktualny)
    {
        test();
        synchronized (monitory) {
            for (ObjectOutputStream out : monitory) {
                try {
                    out.writeUTF("ADD_PARAGON"); // Typ danych
                    out.writeObject(aktualny); // Serializacja obiektu
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void START_NEW_MONITOR(List<Paragon> paragons_waiting, ObjectOutputStream out) throws IOException {

        for(Paragon p : paragons_waiting)
        {
            out.writeUTF("ADD_PARAGON"); // Typ danych
            out.writeObject(p); // Serializacja obiektu
            out.flush();
        }
    }

    public void DELETE_PARAGON(Paragon paragon)
    {
        test();
        synchronized (monitory) {
            for (ObjectOutputStream out : monitory) {
                try {
                    out.reset();
                    out.writeUTF("DELETE_PARAGON"); // Typ danych
                    out.writeObject(paragon.getId()); // Serializacja obiektu
                    out.flush();
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        }
    }
    public void DELETE_PRODUCT(Paragon paragon)
    {
        test();
        synchronized (monitory) {
            for (ObjectOutputStream out : monitory) {
                try {
                    out.reset();
                    out.writeUTF("DELETE_PRODUCT"); // Typ danych
                    out.writeObject(paragon); // Serializacja obiektu
                    out.flush();
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        }
    }

    // Metoda uruchamiająca cykliczne wywoływanie test()
    public void startTestScheduler() {
        testing = true;
        scheduler.scheduleAtFixedRate(() -> {
            try {
                test();  // Wywołanie metody test() co pewien czas
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);  // Pierwsze wywołanie od razu, a potem co 10 sekund
    }

    // Zatrzymanie cyklicznego wywoływania
    public void stopTestScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
