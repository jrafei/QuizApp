package com.quizapp.quizApp.util;

import java.util.UUID;

public class UUIDUtil {

    public static UUID convertHexToUUID(String id) {
        if (id.startsWith("0x")) {
            id = id.substring(2); // Supprime le préfixe "0x"
        }

        if (id.length() != 32) {
            throw new IllegalArgumentException("ID invalide, attendu un format hexadécimal de 32 caractères.");
        }

        String formatted = String.format(
                "%s-%s-%s-%s-%s",
                id.substring(0, 8),
                id.substring(8, 12),
                id.substring(12, 16),
                id.substring(16, 20),
                id.substring(20)
        );
        return UUID.fromString(formatted);
    }
}
