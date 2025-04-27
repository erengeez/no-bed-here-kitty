package erengee.nobedherekitty.mixin;

import erengee.nobedherekitty.NoBedHereKitty;
import net.minecraft.entity.ai.goal.GoToBedAndSleepGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.ai.pathing.Path;

@Mixin(GoToBedAndSleepGoal.class)
public abstract class GoToBeadAndSleepGoalMixin {

	@Shadow @Final private CatEntity cat;

	@Inject(at = @At(value = "RETURN"), method = "isTargetPos", cancellable = true)
	private void checkBedPath(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) {
			boolean hasPathToBed = this.hasPathToBed(pos);
			if (!hasPathToBed) {
				cir.setReturnValue(false);
			}
		}
	}
	@Unique
	private boolean hasPathToBed(BlockPos pos) {
		Path path = this.cat.getNavigation().findPathTo(pos, 0);
		return path != null && path.reachesTarget();
	}
}