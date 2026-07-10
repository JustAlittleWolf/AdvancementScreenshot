package me.wolfii.advancementscreenshot.mixin;

import me.wolfii.advancementscreenshot.Config;
import me.wolfii.advancementscreenshot.client.AdvancementScreenshotClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {
    @Unique
    private boolean screenshotTaken;

    @Inject(method = "update", at = @At("HEAD"))
    private void takeScreenshotOnAdvancement(ToastManager manager, long fullyVisibleForMs, CallbackInfo ci) {
        if (fullyVisibleForMs <= Config.screenshotDelayTicks * 50L) return;
        if (screenshotTaken) return;
        screenshotTaken = true;
        if (AdvancementScreenshotClient.tryTakeScreenshot()) Screenshot.grab(Minecraft.getInstance(), false);
    }
}
