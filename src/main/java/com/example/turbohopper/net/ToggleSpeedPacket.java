package com.example.turbohopper.net;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import com.example.turbohopper.ModProps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ToggleSpeedPacket(BlockPos pos) {

    public static void encode(ToggleSpeedPacket p, FriendlyByteBuf buf) { buf.writeBlockPos(p.pos); }
    public static ToggleSpeedPacket decode(FriendlyByteBuf buf) { return new ToggleSpeedPacket(buf.readBlockPos()); }

    public static void handle(ToggleSpeedPacket p, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (player.containerMenu == player.inventoryMenu) return;            // 画面を開いていない
            if (player.distanceToSqr(Vec3.atCenterOf(p.pos)) > 64) return;       // 遠すぎる

            Level level = player.level();
            BlockState st = level.getBlockState(p.pos);
            Block b = st.getBlock();
            if (!(b instanceof HopperBlock || b instanceof DropperBlock)) return;
            if (!st.hasProperty(ModProps.NORMAL_SPEED)) return;

            BlockState next = st.cycle(ModProps.NORMAL_SPEED);
            level.setBlock(p.pos, next, 3);

            // 即排出モードに切り替えた時、すでに中身があれば排出を開始する
            if (b instanceof DropperBlock
                    && !next.getValue(ModProps.NORMAL_SPEED)
                    && level.getBlockEntity(p.pos) instanceof DropperBlockEntity d
                    && !d.isEmpty()) {
                level.scheduleTick(p.pos, b, 1);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}