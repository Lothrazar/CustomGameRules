package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

@Mixin(FilledMapItem.class)
public class FilledMapItemRefreshMixin {

  // put a map in your inventory, this will trigger every time inventoryTick is triggered 
  @Inject(at = @At("HEAD"), method = "inventoryTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V")
  public void inventoryTickMixin(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected, CallbackInfo info) {
    FilledMapItem map = (FilledMapItem) (Object) this;
    //    Item test = map.asItem();
    if (!worldIn.isRemote &&
        RuleRegistry.isEnabled(worldIn, RuleRegistry.doAlwaysUpdateMap)) {
      MapData mapdata = FilledMapItem.getMapData(stack, worldIn);
      if (mapdata != null) {
        if (entityIn instanceof PlayerEntity) {
          PlayerEntity playerentity = (PlayerEntity) entityIn;
          mapdata.updateVisiblePlayers(playerentity, stack);
        }
        if (!mapdata.locked) {
          map.updateMapData(worldIn, entityIn, mapdata);
        }
      }
    }
  }
}
