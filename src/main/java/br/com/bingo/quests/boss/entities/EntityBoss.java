package br.com.bingo.quests.boss.entities;

import br.com.bingo.Bingo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_21_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R4.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class EntityBoss extends Zombie {

    private  BossBar bossBar;
    private  BukkitRunnable abilityTask;
    private boolean inRage = false;
    private Chunk bossChunk;
    private boolean dead = false;


    public EntityBoss(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle());

        this.setCustomName(Component.literal("Corrupted Champion").withStyle(ChatFormatting.DARK_RED));
        this.setCustomNameVisible(true);
        this.setHealth(400.0f);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400.0);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
        this.setHealth(400.0f);

        this.setPersistenceRequired();

        this.setItemSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.NETHERITE_SWORD)));
        this.setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.NETHERITE_HELMET)));

        this.setPos(loc.getX(), loc.getY(), loc.getZ());

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, net.minecraft.world.entity.player.Player.class, true));

    }

    public void startLogic(){
        bossBar = Bukkit.createBossBar("Corrupted Champion", BarColor.RED, BarStyle.SEGMENTED_10);
        bossBar.setVisible(true);
        bossBar.setProgress(1.0);

        keepChunkLoaded();

        abilityTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (getHealth() <= 0) {
                    EntityBoss.this.die(new DamageSource(null));
                    this.cancel();
                    return;
                }

                if (isRemoved()) {
                    bossBar.removeAll();
                    this.cancel();
                    return;
                }

                updateBossBar();
                runAbilities();
            }
        };
        abilityTask.runTaskTimer(Bingo.getInstance(), 60L, 60L); // 1 vez por 3s
    }

    private void updateBossBar() {

        double progress = Math.max(0.0, this.getHealth() / this.getMaxHealth());
        bossBar.setProgress(progress);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(this.getBukkitEntity().getLocation()) < 30) {
                if (!bossBar.getPlayers().contains(player)) {
                    bossBar.addPlayer(player);
                }
            } else {
                bossBar.removePlayer(player);
            }
        }
    }

    private void runAbilities() {
        double healthPercent = this.getHealth() / this.getMaxHealth();

        if (healthPercent <= 0.3 && !inRage) {
            enterRageMode();
        }


        int random = new Random().nextInt(100);
        if (random < 20) {
            useSwordBarrage();
        } else if (random < 40) {
            useShadowClone();
        } else if (random < 60) {
            useDarkPulse();
        } else if (random < 70) {
            launchWitherSkull();
        }
    }

    private void useSwordBarrage() {
        Player nearest = getNearestPlayer();
        if (nearest == null || this.getBukkitEntity().getLocation().distance(nearest.getLocation()) > 40) return;

        Location start = this.getBukkitEntity().getLocation();
        Location end = nearest.getLocation();

        drawParticleTrail(start, end);
        this.teleportTo(end.getX(), end.getY(), end.getZ());
        double damage = 8;

        if(inRage) damage = 12;

        for (int i = 0; i < 3; i++) {
            nearest.damage(calcularDanoReal(nearest, damage));
            nearest.setVelocity(nearest.getLocation().getDirection().multiply(-0.3));
        }
        end.getWorld().spawnParticle(Particle.SWEEP_ATTACK, end, 10);

    }

    private void useShadowClone() {
        Location loc = this.getBukkitEntity().getLocation();
        for (int i = 0; i < 2; i++) {
            org.bukkit.entity.Zombie clone = (org.bukkit.entity.Zombie) loc.getWorld().spawnEntity(loc.clone().add(Math.random() * 3 - 1.5, 0, Math.random() * 3 - 1.5), EntityType.ZOMBIE);
            clone.setCustomName("Corrupted Clone");
            clone.setCustomNameVisible(false);
            clone.setBaby(true);
            clone.setHealth(10);
            clone.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!clone.isDead()) {
                        clone.remove();
                    }
                }
            }.runTaskLater(Bingo.getProvidingPlugin(getClass()), 200L);
        }
    }

    private void useDarkPulse() {
        Location loc = this.getBukkitEntity().getLocation();
        spawnGrowingCircle(loc);
        loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_SPAWN, 1, 0.6f);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().distance(loc) < 6) {
                Vector direction = p.getLocation().toVector().subtract(loc.toVector()).normalize();
                direction.setY(1);
                p.setVelocity(direction.multiply(1.2));
                p.damage(6);
            }
        }
    }

    private void enterRageMode() {
        inRage = true;
        startRageRegen();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);

        Location loc = this.getBukkitEntity().getLocation();
        spawnGrowingCircle(loc);
        loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0.8f);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getHealth() <= 0 || isRemoved() || !inRage) {
                    this.cancel();
                    return;
                }
                Location loc = getBukkitEntity().getLocation();
                loc.getWorld().spawnParticle(Particle.FLAME, loc.clone().add(0, 1.5, 0), 10, 0.4, 0.4, 0.4, 0.02);
            }
        }.runTaskTimer(Bingo.getProvidingPlugin(getClass()), 0L, 10L);


    }

    private void spawnGrowingCircle(Location center) {
        World world = center.getWorld();
        new BukkitRunnable() {

            double radius = 1.0;
            final double maxRadius = 6.0;
            @Override
            public void run() {
                if (radius > maxRadius) {
                    this.cancel();
                    return;
                }
                for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 32) {
                    double x = center.getX() + radius * Math.cos(angle);
                    double z = center.getZ() + radius * Math.sin(angle);
                    Location particleLoc = new Location(world, x, center.getY() + 0.2, z);
                    world.spawnParticle(Particle.WITCH, particleLoc, 1, 0, 0, 0, 0);
                }
                radius += 0.5;
            }
        }.runTaskTimer(Bingo.getProvidingPlugin(getClass()), 0L, 10L);
    }

    private void launchWitherSkull() {
        Player target = getNearestPlayer();
        if (target == null) return;

        Location eyeLoc = this.getBukkitEntity().getLocation().add(0, 1.5, 0);
        Vector baseDir = target.getLocation().add(0, 1, 0).toVector().subtract(eyeLoc.toVector()).normalize();

        for (int i = -1; i <= 1; i++) {
            Vector offset = baseDir.clone().add(new Vector(0.1 * i, 0, 0)).normalize();
            WitherSkull skull = (WitherSkull) this.getBukkitEntity().getWorld().spawnEntity(eyeLoc, EntityType.WITHER_SKULL);
            skull.setDirection(offset);
            skull.setShooter((LivingEntity) this.getBukkitEntity());
        }
    }

    private Player getNearestPlayer() {
        Location loc = this.getBukkitEntity().getLocation();
        Player nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Player p : Bukkit.getOnlinePlayers()) {
            double dist = p.getLocation().distance(loc);
            if (dist < minDist) {
                minDist = dist;
                nearest = p;
            }
        }
        return nearest;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!dead) {
            dead = true;
            abilityTask.cancel();
            bossBar.removeAll();
            releaseChunk();
        }
        super.remove(reason);
    }

    private void startRageRegen() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getHealth() <= 0 || isRemoved() || !inRage) {
                    this.cancel();
                    return;
                }
                float currentHealth = getHealth();
                float max = getMaxHealth();
                if (currentHealth < max) {
                    setHealth(Math.min(max, currentHealth + 12.0f));
                }
            }
        }.runTaskTimer(Bingo.getProvidingPlugin(getClass()), 0L, 400L);
    }

    private void drawParticleTrail(Location from, Location to) {
        Vector path = to.toVector().subtract(from.toVector());
        double length = path.length();
        path.normalize();

        for (double d = 0; d < length; d += 0.5) {
            Location point = from.clone().add(path.clone().multiply(d));
            point.getWorld().spawnParticle(Particle.PORTAL, point, 1, 0.1, 0.1, 0.1, 0.01);
        }
    }

    public void spawnBoss(Location location){
        keepChunkLoaded();
        ServerLevel nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityBoss boss = new EntityBoss(location);
        nmsWorld.addFreshEntity(boss, CreatureSpawnEvent.SpawnReason.CUSTOM);
        boss.startLogic();
    }

    public double calcularDanoReal(Player player, double danoOriginal) {
        int defesaTotal = 0;

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null || item.getType() == Material.AIR) continue;

            switch (item.getType()) {
                case LEATHER_HELMET: case LEATHER_BOOTS: defesaTotal += 1; break;
                case LEATHER_LEGGINGS: defesaTotal += 2; break;
                case LEATHER_CHESTPLATE: defesaTotal += 3; break;

                case GOLDEN_HELMET: case GOLDEN_BOOTS: defesaTotal += 2; break;
                case GOLDEN_LEGGINGS: defesaTotal += 3; break;
                case GOLDEN_CHESTPLATE: defesaTotal += 5; break;

                case CHAINMAIL_HELMET: case CHAINMAIL_BOOTS: defesaTotal += 2; break;
                case CHAINMAIL_LEGGINGS: defesaTotal += 4; break;
                case CHAINMAIL_CHESTPLATE: defesaTotal += 5; break;

                case IRON_HELMET: defesaTotal += 2; break;
                case IRON_BOOTS: defesaTotal += 2; break;
                case IRON_LEGGINGS: defesaTotal += 5; break;
                case IRON_CHESTPLATE: defesaTotal += 6; break;

                case DIAMOND_HELMET: case NETHERITE_HELMET: defesaTotal += 3; break;
                case DIAMOND_BOOTS: case NETHERITE_BOOTS: defesaTotal += 3; break;
                case DIAMOND_LEGGINGS: case NETHERITE_LEGGINGS: defesaTotal += 6; break;
                case DIAMOND_CHESTPLATE: case NETHERITE_CHESTPLATE: defesaTotal += 8; break;
                default: break;
            }
        }

        // Redução baseada em defesa total (máx efetivo é 20)
        double reducao = Math.min(20, defesaTotal) / 25.0;
        return danoOriginal * (1.0 - reducao);
    }

    public boolean isAlive(){
        return !dead && this.getHealth() > 0 && !this.isRemoved();
    }

    private void keepChunkLoaded() {
        Location loc = getBukkitEntity().getLocation();
        bossChunk = loc.getChunk();
        bossChunk.setForceLoaded(true); // mantém a chunk carregada
    }

    private void releaseChunk() {
        if (bossChunk != null) {
            bossChunk.setForceLoaded(false);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (dead) return; // já morreu antes
        dead = true;

        Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
        releaseChunk();
        abilityTask.cancel(); // garantir cancelamento da task
        bossBar.setVisible(false);
    }




}
