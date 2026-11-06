package com.example.turbohopper.mixin;

import com.example.turbohopper.ModProps;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperCooldownMixin {
    private static final int FAST_COOLDOWN = 1;

    @ModifyVariable(method = "setCooldown", at = @At("HEAD"), argsOnly = true)
    private int turbohopper$fastCooldown(int original) {
        if (original <= 0) return original;
        HopperBlockEntity self = (HopperBlockEntity) (Object) this;
        Level level = self.getLevel();
        if (level == null) return original;

        BlockState st = level.getBlockState(self.getBlockPos());
        if (st.hasProperty(ModProps.NORMAL_SPEED) && !st.getValue(ModProps.NORMAL_SPEED)) {
            return Math.min(original, FAST_COOLDOWN);
        }
        return original;
    }
}