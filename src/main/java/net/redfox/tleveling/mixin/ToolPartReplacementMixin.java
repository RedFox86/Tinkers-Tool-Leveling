package net.redfox.tleveling.mixin;

import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.leveling.ToolExp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tables.recipe.TinkerStationPartSwapping;

public class ToolPartReplacementMixin {

  @Mixin(TinkerStationPartSwapping.class)
  public static class RecipeMixin {

    @Redirect(
        method = "getValidatedResult",
        at = @At(
            value = "INVOKE",
            target = "Lslimeknights/tconstruct/library/tools/nbt/ToolStack;replaceMaterial(ILslimeknights/tconstruct/library/materials/definition/MaterialVariantId;)V"
        ),
        remap = false
    )
    private void onReplaceMaterial(ToolStack instance, int index, MaterialVariantId replacement) {
      instance.replaceMaterial(index, replacement);
      if (TinkersLevelingCommonConfigs.EXP_LOSS_ON_REPLACE.get() == 1) return;
      ItemStack stack = instance.createStack();
      if (TinkersLevelingCommonConfigs.EXP_LOSS_ON_REPLACE.get() == 0) {
        ToolExp.setToolExp(stack, 0);
      } else {
        ToolExp.setToolExp(stack, ToolExp.getCurrentExp(stack)/TinkersLevelingCommonConfigs.EXP_LOSS_ON_REPLACE.get());
      }
    }
  }
}
