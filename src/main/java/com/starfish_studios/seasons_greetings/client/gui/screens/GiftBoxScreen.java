package com.starfish_studios.seasons_greetings.client.gui.screens;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.inventory.GiftBoxMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class GiftBoxScreen extends AbstractContainerScreen<GiftBoxMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = SeasonsGreetings.id("textures/gui/gift_box.png");

    public GiftBoxScreen(GiftBoxMenu giftBoxMenu, Inventory inventory, Component component) {
        super(giftBoxMenu, inventory, component);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY += 16;
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_LOCATION, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}