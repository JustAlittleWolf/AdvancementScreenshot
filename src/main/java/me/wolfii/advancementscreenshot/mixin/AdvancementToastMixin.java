package me.wolfii.advancementscreenshot.mixin;

import me.wolfii.advancementscreenshot.Config;
import me.wolfii.advancementscreenshot.client.AdvancementScreenshotClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.ScreenshotRecorder;
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
    private void takeScreenshotOnAdvancement(ToastManager manager, long time, CallbackInfo ci) {
        if (time <= Config.screenshotDelayTicks * 50L) return;
        if (screenshotTaken) return;
        screenshotTaken = true;
        if (AdvancementScreenshotClient.tryTakeScreenshot()) {
            MinecraftClient client = MinecraftClient.getInstance();
            ScreenshotRecorder.saveScreenshot(client.runDirectory, client.getFramebuffer(), message -> {
            });
        }
    }
}
