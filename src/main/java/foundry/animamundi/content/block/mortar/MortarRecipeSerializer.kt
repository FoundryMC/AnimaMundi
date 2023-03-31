package foundry.animamundi.content.block.mortar

import com.google.gson.JsonObject
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.ShapedRecipe

class MortarRecipeSerializer(recipeType: RecipeType<MortarRecipe>): RecipeSerializer<MortarRecipe> {
    private var recipeType: RecipeType<MortarRecipe>
    init {
        this.recipeType = recipeType
    }
    override fun fromJson(pRecipeId: ResourceLocation, pSerializedRecipe: JsonObject): MortarRecipe {
        var groupName: String = pSerializedRecipe.get("group").asString
        var ingredients: NonNullList<Ingredient> = NonNullList.create()
        var result: ItemStack = ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject("result"))
        var experience: Float = pSerializedRecipe.get("experience").asFloat
        var ingredient: Ingredient = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("ingredient"))
        ingredients.add(ingredient)
        return MortarRecipe(this.recipeType, pRecipeId, groupName, ingredients, result, experience)
    }

    override fun fromNetwork(pRecipeId: ResourceLocation, pBuffer: FriendlyByteBuf): MortarRecipe? {
        var groupName: String = pBuffer.readUtf(32767)
        var ingredients: NonNullList<Ingredient> = NonNullList.create()
        var ingredientCount: Int = pBuffer.readVarInt()
        for(i in 0 until ingredientCount) {
            ingredients.add(Ingredient.fromNetwork(pBuffer))
        }
        var result: ItemStack = pBuffer.readItem()
        var experience: Float = pBuffer.readFloat()
        return MortarRecipe(this.recipeType, pRecipeId, groupName, ingredients, result, experience)
    }

    override fun toNetwork(pBuffer: FriendlyByteBuf, pRecipe: MortarRecipe) {
        pBuffer.writeUtf(pRecipe.group)
        pBuffer.writeVarInt(pRecipe.ingredients.size)
        for(ingredient in pRecipe.ingredients) {
            ingredient.toNetwork(pBuffer)
        }
        pBuffer.writeItem(pRecipe.result)
        pBuffer.writeFloat(pRecipe.experience)
    }
}