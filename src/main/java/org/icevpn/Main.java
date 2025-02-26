package org.icevpn;

import com.pengrad.telegrambot.TelegramBot;
import org.icevpn.Telegram.*;
import org.springframework.boot.SpringApplication;

public class Main {
    public static UpdateListener updateListener;
    public static Options options;
    public static Menu menu;
    public static Buy buy;
    public static Help help;
    public static Payment payment;
    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);
    public static TelegramBot bot;
    public static org.icevpn.DB db;


    public static void main(String[] args) {
        bot = new TelegramBot("8128860304:AAFBqoFp6JTMpRGrENj9L64EdHFt5HgEh9k");
        SpringApplication.run(SpringApp.class, args);

        db = new DB();
        options = new Options();
        buy = new Buy();
        menu = new Menu();
        help = new Help();
        payment = new Payment();
        updateListener = new UpdateListener();
        updateListener.main(args);
        System.out.println("Запущено!");

    }

    public static Payment getPayment() {
        return payment;
    }
}