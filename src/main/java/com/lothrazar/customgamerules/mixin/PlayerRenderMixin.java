package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.text.ITextComponent;

@Mixin(PlayerRenderer.class)
public class PlayerRenderMixin {

  @Inject(at = @At(value = "HEAD"), method = "renderName(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/util/text/ITextComponent;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", cancellable = true)
  public void tickMixin(AbstractClientPlayerEntity entityIn, ITextComponent displayNameIn,
      MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
      CallbackInfo info) {
    //    if (RuleRegistry.isEnabled(entityIn.world, RuleRegistry.disableLightMeltIce)) {
    //      info.cancel();
    //    }
  }
}
