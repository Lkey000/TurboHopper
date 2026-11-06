package com.example.turbohopper.mixin;

import com.example.turbohopper.ModProps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperBlock.class)
public abstract class HopperStateMixin {
    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void turbohopper$addProp(StateDefinition.Builder<Block, BlockState> b, CallbackInfo ci) {
        b.add(ModProps.NORMAL_SPEED);
    }
}