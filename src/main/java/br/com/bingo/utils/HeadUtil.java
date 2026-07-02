package br.com.bingo.utils;

import br.com.bingo.Bingo;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Utilitário para aplicar texturas customizadas (skins) em cabeças (PLAYER_HEAD).
 *
 * Centraliza o "truque" de reflection sobre o CraftMetaSkull + ResolvableProfile do NMS,
 * que muda a cada versão do Minecraft. Ao concentrar tudo aqui, uma migração futura só
 * precisa ajustar este único ponto (antes eram 6 blocos duplicados).
 */
public final class HeadUtil {

    private HeadUtil() {
    }

    /**
     * Aplica uma textura customizada (valor base64 da propriedade "textures") a uma SkullMeta.
     *
     * O UUID do perfil é derivado da própria textura (determinístico), então não é preciso
     * armazenar/gerenciar um UUID separado para cada cabeça — basta a textura.
     */
    public static void applyTexture(SkullMeta meta, String texture) {
        if (meta == null || texture == null || texture.isEmpty()) {
            return;
        }

        UUID profileId = UUID.nameUUIDFromBytes(texture.getBytes(StandardCharsets.UTF_8));
        PropertyMap properties = new PropertyMap(ImmutableMultimap.of("textures", new Property("textures", texture)));
        GameProfile profile = new GameProfile(profileId, "head", properties);

        try {
            // ResolvableProfile é abstrato desde a 26.1: usa a fábrica estática createResolved(GameProfile).
            Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                    .getMethod("createResolved", GameProfile.class)
                    .invoke(null, profile);

            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, resolvableProfile);
        } catch (ReflectiveOperationException e) {
            Bingo.getInstance().getLogger().log(Level.WARNING,
                    "Não foi possível aplicar textura customizada na cabeça (reflection NMS falhou)", e);
        }
    }
}
