package com.example.turbohopper.mixin;

import com.example.turbohopper.ModProps;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RandomizableContainerBlockEntity.class)
public abstract class RandomizableContainerMixin {

    private static final int DROP_DELAY = 1;    // 排出できている間の間隔(tick)
    private static final int RETRY_DELAY = 10;  // 排出先が満杯の間の再試行の間隔(tick)

    @Inject(method = "setItem", at = @At("HEAD"))
    private void turbohopper$autoDrop(int slot, ItemStack stack, CallbackInfo ci) {
        RandomizableContainerBlockEntity self = (RandomizableContainerBlockEntity) (Object) this;
        if (!(self instanceof DropperBlockEntity)) return;

        Level level = self.getLevel();
        if (level == null || level.isClientSide) return;

        // 高速(即排出)モードの時だけ動かす
        BlockState st = level.getBlockState(self.getBlockPos());
        if (!st.hasProperty(ModProps.NORMAL_SPEED) || st.getValue(ModProps.NORMAL_SPEED)) return;

        // この更新のあとも、中にアイテムが残るか
        boolean hasItems = !stack.isEmpty();
        for (int i = 0; i < self.getContainerSize() && !hasItems; i++) {
            if (i != slot && !self.getItem(i).isEmpty()) hasItems = true;
        }
        if (!hasItems) return;   // 空になったので、予約しない

        // 中身が変化していない = 排出先が満杯で、同じ内容が書き戻されただけ
        // → 毎tickではなく、間隔を空けて再試行する(空きができたら自動で再開する)
        boolean blocked = ItemStack.matches(self.getItem(slot), stack);
        int delay = blocked ? RETRY_DELAY : DROP_DELAY;

        level.scheduleTick(self.getBlockPos(), st.getBlock(), delay);
    }
}