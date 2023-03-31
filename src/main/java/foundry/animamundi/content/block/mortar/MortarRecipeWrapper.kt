package foundry.animamundi.content.block.mortar

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.RecipeWrapper

class MortarRecipeWrapper(inv: IItemHandlerModifiable?) : RecipeWrapper(inv) {
    private var toCrush: IItemHandlerModifiable = inv!!
    private var recipes: MutableList<Recipe<MortarRecipeWrapper>> = mutableListOf()

    init {
        val slot = inv!!.slots
        toCrush = ItemStackHandler(slot)
        for (slot in 0 until slot) {
            var stackCopy: ItemStack = inv.getStackInSlot(slot).copy()
            for (i in 0 until stackCopy.count) {
                toCrush.insertItem(slot, stackCopy, false)
            }
        }
    }

    fun getRecipes(): MutableList<Recipe<MortarRecipeWrapper>> {
        return recipes
    }

    fun getToCrush(): IItemHandlerModifiable {
        return toCrush
    }

    fun getRecipeCount(): Int {
        return recipes.size
    }

    fun matches(recipe: Recipe<MortarRecipeWrapper>, level: Level): Boolean {
        return recipe.matches(this, level)
    }
}