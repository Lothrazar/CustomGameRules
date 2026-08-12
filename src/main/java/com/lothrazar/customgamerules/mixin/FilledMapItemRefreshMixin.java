package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

@Mixin(MapItem.class)
public class FilledMapItemRefreshMixin {

  // put a map in your inventory, this will trigger every time inventoryTick is triggered
  // Signature changed from (ItemStack, Level, Entity, int, boolean) - Mojang replaced the raw
  // slot index + selected boolean with a single EquipmentSlot, and narrowed Level to ServerLevel.
  // Confirmed against the actual 26.1.2 game jar via Mixin's mixin.dumpTargetOnFailure output.
  @Inject(at = @At("HEAD"), method = "inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/EquipmentSlot;)V", remap = false)
  public void inventoryTickMixin(ItemStack stack, ServerLevel worldIn, Entity entityIn, EquipmentSlot slot, CallbackInfo info) {
    if (!worldIn.isClientSide() &&
        RuleRegistry.isEnabled(worldIn, RuleRegistry.doMapsAlwaysUpdate)) {
      ModGameRule.LOGGER.debug("FilledMapItemRefreshMixin rule doMapsAlwaysUpdate=true");
      MapItemSavedData mapdata = MapItem.getSavedData(stack, worldIn);
      if (mapdata != null) {
        MapItem map = (MapItem) (Object) this;
        if (entityIn instanceof Player playerentity) {
          mapdata.tickCarriedBy(playerentity, stack, null);
        }
        if (!mapdata.locked) {
          map.update(worldIn, entityIn, mapdata);
        }
      }
    }
  }
}
