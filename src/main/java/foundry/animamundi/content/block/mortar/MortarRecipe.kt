package foundry.animamundi.content.block.mortar

import foundry.animamundi.grimoire.AnimaMundiRecipeRegistry
import net.minecraft.core.NonNullList
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraftforge.items.IItemHandler

class MortarRecipe(type: RecipeType<*>, id:ResourceLocation, group: String, ingredients: NonNullList<Ingredient>, result: ItemStack, experience: Float): Recipe<MortarRecipeWrapper> {
    private var type: RecipeType<*>
    private var id: ResourceLocation
    private var group: String
    private var ingredients: NonNullList<Ingredient>
    var result: ItemStack
    var experience: Float
    init {
        this.type = type
        this.id = id
        this.group = group
        this.ingredients = ingredients
        this.result = result
        this.experience = experience
    }
    override fun matches(pContainer: MortarRecipeWrapper, pLevel: Level): Boolean {
        var input: IItemHandler = pContainer.getToCrush()
        // check if input matches ingredient
        for(ingredient in ingredients) {
            if(ingredient.test(input.getStackInSlot(0))) {
                input.extractItem(0, 1, false)
                return true
            }
        }
        return false
    }

    override fun assemble(pContainer: MortarRecipeWrapper): ItemStack {
        return this.result.copy()
    }

    override fun canCraftInDimensions(pWidth: Int, pHeight: Int): Boolean {
        return true
    }

    override fun getResultItem(): ItemStack {
        return this.result
    }

    override fun getId(): ResourceLocation {
        return this.id
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return AnimaMundiRecipeRegistry.MORTAR_SERIALIZER.get()
    }

    override fun getType(): RecipeType<*> {
        return this.type
    }

    override fun getRemainingItems(pContainer: MortarRecipeWrapper): NonNullList<ItemStack> {
        return NonNullList.withSize(pContainer.getToCrush().slots, ItemStack.EMPTY)
    }
}