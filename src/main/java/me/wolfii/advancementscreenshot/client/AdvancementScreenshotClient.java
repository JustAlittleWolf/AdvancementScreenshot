package me.wolfii.advancementscreenshot.client;

import me.wolfii.advancementscreenshot.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.concurrent.atomic.AtomicInteger;

public class AdvancementScreenshotClient implements ClientModInitializer {
    private static final AtomicInteger screenshotCooldown = new AtomicInteger(0);

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((_) -> screenshotCooldown.getAndUpdate(it -> Math.max(it - 1, 0)));
    }

    public static boolean tryTakeScreenshot() {
        return screenshotCooldown.getAndUpdate(it -> it > 0 ? it : Config.screenshotCooldownTicks) <= 0;
    }
}
