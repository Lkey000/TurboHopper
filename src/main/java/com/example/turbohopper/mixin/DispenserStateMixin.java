package com.example.turbohopper.mixin;

import com.example.turbohopper.ModProps;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public abstract class DispenserStateMixin {

    // 即排出モードで、1tickに排出する最大個数(バニラの1回分を含む)
    private static final int EJECT_PER_TICK = 16;

    @Shadow
    protected abstract void dispenseFrom(ServerLevel level, BlockPos pos);

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void turbohopper$addProp(StateDefinition.Builder<Block, BlockState> b, CallbackInfo ci) {
        b.add(ModProps.NORMAL_SPEED);
    }

    // バニラの排出(1回)のあとに、続けて排出する
    @Inject(method = "tick", at = @At("TAIL"))
    private void turbohopper$burst(BlockState state, ServerLevel level, BlockPos pos,
                                   RandomSource random, CallbackInfo ci) {
        if (!((Object) this instanceof DropperBlock)) return;   // ディスペンサーは対象外
        if (!state.hasProperty(ModProps.NORMAL_SPEED) || state.getValue(ModProps.NORMAL_SPEED)) return;

        for (int n = 1; n < EJECT_PER_TICK; n++) {
            if (!(level.getBlockEntity(pos) instanceof DropperBlockEntity d) || d.isEmpty()) return;

            int before = countItems(d);
            this.dispenseFrom(level, pos);
            if (countItems(d) >= before) return;   // 排出先が満杯などで進まなければ、やめる
        }
    }

    private static int countItems(DropperBlockEntity d) {
        int total = 0;
        for (int i = 0; i < d.getContainerSize(); i++) {
            total += d.getItem(i).getCount();
        }
        return total;
    }
}