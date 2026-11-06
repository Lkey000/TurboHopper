package com.example.turbohopper.net;

import com.example.turbohopper.TurboHopper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNet {
    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TurboHopper.MOD_ID, "main"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void init() {
        CHANNEL.registerMessage(0, ToggleSpeedPacket.class,
                ToggleSpeedPacket::encode, ToggleSpeedPacket::decode, ToggleSpeedPacket::handle);
    }
}