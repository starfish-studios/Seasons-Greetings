package com.starfish_studios.seasons_greetings.common.entity;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CantCatchMeGoal extends Goal {
    private final GingerbreadMan gingerbreadMan;
    private final double speedModifier;
    private double posX;
    private double posY;
    private double posZ;

    public CantCatchMeGoal(GingerbreadMan gingerbreadMan, double speedModifier) {
        this.gingerbreadMan = gingerbreadMan;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (this.gingerbreadMan.isCantCatchMe(true)) {
            Vec3 vec3 = DefaultRandomPos.getPos(this.gingerbreadMan, 5, 4);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.gingerbreadMan.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
    }

    public boolean canContinueToUse() {
        return !this.gingerbreadMan.getNavigation().isDone() && this.gingerbreadMan.isVehicle();
    }
}