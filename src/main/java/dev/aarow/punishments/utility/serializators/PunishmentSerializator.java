package dev.aarow.punishments.utility.serializators;

import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PunishmentSerializator {

    private static final String FIELD_DELIMITER = ";"; // Delimiter for fields within a Punishment
    private static final String PUNISHMENT_DELIMITER = "|"; // Delimiter for each Punishment

    public static String serialize(List<Punishment> punishments) {
        StringBuilder builder = new StringBuilder();
        for (Punishment punishment : punishments) {
            builder.append(punishment.getUuid()).append(FIELD_DELIMITER)
                    .append(punishment.getPunishmentType().name()).append(FIELD_DELIMITER)
                    .append(punishment.getReason()).append(FIELD_DELIMITER)
                    .append(punishment.getPunishedAt()).append(FIELD_DELIMITER)
                    .append(punishment.getDuration()).append(FIELD_DELIMITER)
                    .append(punishment.getPunishedBy()).append(FIELD_DELIMITER)
                    .append(punishment.getLocalAddress() != null ? punishment.getLocalAddress() : "").append(FIELD_DELIMITER)
                    .append(punishment.isActive()).append(PUNISHMENT_DELIMITER);
        }
        if (!punishments.isEmpty()) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    public static List<Punishment> deserialize(String serializedData) {
        List<Punishment> punishments = new ArrayList<>();
        if (serializedData == null || serializedData.isEmpty()) {
            return punishments;
        }

        String[] serializedPunishments = serializedData.split("\\" + PUNISHMENT_DELIMITER);
        for (String serializedPunishment : serializedPunishments) {
            String[] fields = serializedPunishment.split(FIELD_DELIMITER);
            if (fields.length != 8) {
                continue;
            }

            try {
                UUID uuid = UUID.fromString(fields[0]);
                PunishmentType punishmentType = PunishmentType.valueOf(fields[1]);
                String reason = fields[2];
                long punishedAt = Long.parseLong(fields[3]);
                long duration = Long.parseLong(fields[4]);
                String punishedBy = fields[5];
                String localAddress = !fields[6].isEmpty() ? fields[6] : null;
                boolean active = Boolean.parseBoolean(fields[7]);

                Punishment punishment = new Punishment(uuid, punishmentType, reason, punishedAt, duration, punishedBy, active).setLocalAddress(localAddress);
                punishments.add(punishment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return punishments;
    }
}
