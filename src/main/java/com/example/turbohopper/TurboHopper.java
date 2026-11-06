package com.example.turbohopper;

import com.example.turbohopper.net.ModNet;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TurboHopper.MOD_ID)
public class TurboHopper {
    public static final String MOD_ID = "turbohopper";

    public TurboHopper() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((FMLCommonSetupEvent e) -> e.enqueueWork(ModNet::init));
    }
}