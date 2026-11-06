package com.example.turbohopper.client;

import com.example.turbohopper.ModProps;
import com.example.turbohopper.TurboHopper;
import com.example.turbohopper.net.ModNet;
import com.example.turbohopper.net.ToggleSpeedPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TurboHopper.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpeedButtonHandler {

    private static Component label(boolean fast, boolean dropper) {
        String key = TurboHopper.MOD_ID + (dropper ? ".drop." : ".speed.") + (fast ? "fast" : "normal");
        return Component.translatable(key);
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post e) {
        Screen s = e.getScreen();
        if (!(s instanceof HopperScreen || s instanceof DispenserScreen)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || !(mc.hitResult instanceof BlockHitResult hit)) return;

        BlockPos pos = hit.getBlockPos();
        BlockState st = mc.level.getBlockState(pos);
        Block b = st.getBlock();
        boolean dropper = b instanceof DropperBlock;
        if (!(b instanceof HopperBlock || dropper)) return;   // ディスペンサーには付けない
        if (!st.hasProperty(ModProps.NORMAL_SPEED)) return;

        AbstractContainerScreen<?> acs = (AbstractContainerScreen<?>) s;
        boolean[] fast = { !st.getValue(ModProps.NORMAL_SPEED) };

        Button btn = Button.builder(label(fast[0], dropper), self -> {
            ModNet.CHANNEL.sendToServer(new ToggleSpeedPacket(pos));
            fast[0] = !fast[0];                       // サーバーの反映を待たずに表示だけ先に更新
            self.setMessage(label(fast[0], dropper));
        }).bounds(acs.getGuiLeft() + acs.getXSize() + 4, acs.getGuiTop() + 4, 80, 20).build();

        e.addListener(btn);
    }
}