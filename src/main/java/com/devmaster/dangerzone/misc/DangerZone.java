package com.devmaster.dangerzone.misc;

import com.devmaster.dangerzone.client.render.NotBreeBreeRender;
import com.devmaster.dangerzone.client.render.StampyLongNoseRender;
import com.devmaster.dangerzone.client.render.TewtiyRender;
import com.devmaster.dangerzone.entity.NotBreeBree;
import com.devmaster.dangerzone.entity.StampyLongNose;
import com.devmaster.dangerzone.entity.Tewtiy;
import com.devmaster.dangerzone.util.RegistryHandler;
import com.devmaster.dangerzone.world.gen.ModOregen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod("dangerzone")
public class DangerZone {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "dangerzone";

    public DangerZone() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DZConfig.spec);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, EventPriority.LOW, ModOregen::addConfigFeatures);

        MinecraftForge.EVENT_BUS.addListener(ModOregen::handleWorldGen);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(RegistryHandler.TEWTIY.get(), Tewtiy.getAttributes().create());
            GlobalEntityTypeAttributes.put(RegistryHandler.STAMPYLONGNOSE.get(), StampyLongNose.getAttributes().create());
            GlobalEntityTypeAttributes.put(RegistryHandler.NOTBREEBREE.get(), NotBreeBree.getAttributes().create());

        });
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(RegistryHandler.STICKY_BLOCK.get(), RenderType.getCutout());
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.TEWTIY.get(), TewtiyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.STAMPYLONGNOSE.get(), StampyLongNoseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.NOTBREEBREE.get(), NotBreeBreeRender::new);

    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        List<MobSpawnInfo.Spawners> spawns =
                event.getSpawns().getSpawner(EntityClassification.MONSTER);

        spawns.add(new MobSpawnInfo.Spawners(RegistryHandler.TEWTIY.get(), 1, 1, 1));
        spawns.add(new MobSpawnInfo.Spawners(RegistryHandler.STAMPYLONGNOSE.get(), 1, 1, 1));
        spawns.add(new MobSpawnInfo.Spawners(RegistryHandler.NOTBREEBREE.get(), 1, 1, 1));

    }

    private void entitySpawn() {

    }
    public static final ItemGroup TAB = new ItemGroup("dangerzonetab") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.AMETHYST.get());
        }
    };
    public static final ItemGroup BLOCKS = new ItemGroup("dangerzoneblocks") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.AMETHYST_BLOCK.get());
        }
    };
    public static final ItemGroup TOOLS = new ItemGroup("dangerzonetools") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.AMETHYST_AXE.get());
        }
    };
    public static final ItemGroup WEAPONS = new ItemGroup("dangerzoneweapons") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.AMETHYST_SWORD.get());
        }
    };
    public static final ItemGroup FOOD = new ItemGroup("dangerzonefood") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.FIRE_FISH.get());
        }
    };
    public static final ItemGroup ARMOR = new ItemGroup("dangerzonearmory") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.AMETHYST_CHESTPLATE.get());
        }
    };

}


