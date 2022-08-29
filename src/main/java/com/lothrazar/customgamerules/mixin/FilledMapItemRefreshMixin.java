package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

@Mixin(MapItem.class)
public class FilledMapItemRefreshMixin {

  // put a map in your inventory, this will trigger every time inventoryTick is triggered 
  @Inject(at = @At("HEAD"), method = "inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;IZ)V")
  public void inventoryTickMixin(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected, CallbackInfo info) {
    if (!worldIn.isClientSide &&
        RuleRegistry.isEnabled(worldIn, RuleRegistry.doMapsAlwaysUpdate)) {
      GameRuleMod.info("FilledMapItemRefreshMixin rule doMapsAlwaysUpdate=true");
      MapItemSavedData mapdata = MapItem.getSavedData(stack, worldIn);
      if (mapdata != null) {
        MapItem map = (MapItem) (Object) this;
        if (entityIn instanceof Player playerentity) {
          mapdata.tickCarriedBy(playerentity, stack);
        }
        if (!mapdata.locked) {
          map.update(worldIn, entityIn, mapdata);
        }
      }
    }
  }
}
