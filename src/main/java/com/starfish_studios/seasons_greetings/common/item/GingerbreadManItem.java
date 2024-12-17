package com.starfish_studios.seasons_greetings.common.item;

import com.starfish_studios.seasons_greetings.common.entity.GingerbreadMan;
import com.starfish_studios.seasons_greetings.registry.SGEntityType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GingerbreadManItem extends Item {
    public GingerbreadManItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            GingerbreadMan gingerbreadMan = SGEntityType.GINGERBREAD_MAN.get().create(context.getLevel());
            assert gingerbreadMan != null;

            gingerbreadMan.setTame(true, true);
            gingerbreadMan.setOwnerUUID(Objects.requireNonNull(context.getPlayer()).getUUID());
            gingerbreadMan.setPos(context.getClickLocation());
            context.getLevel().addFreshEntity(gingerbreadMan);
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
