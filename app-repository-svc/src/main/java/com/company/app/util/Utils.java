package com.company.app.util;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static String generadorReferencia(){
        int numeroAleatorio = ThreadLocalRandom.current().nextInt(0, 1000000);
        return String.format("%06d", numeroAleatorio);
    }

}
