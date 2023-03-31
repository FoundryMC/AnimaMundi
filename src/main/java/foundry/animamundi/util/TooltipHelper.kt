package foundry.animamundi.util

import joptsimple.internal.Strings
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object TooltipHelper {
    fun makeProgressBar(progress: Float, lightColor: Int, darkColor: Int): Component {
        var fontRenderer: Font = Minecraft.getInstance().font
        var char: Int = fontRenderer.width("|")
        var tip: Int = fontRenderer.width("Tooltip Bar Progress")
        var max: Int = tip / char
        var total: Int = (progress * max).toInt()
        var bars: String = ""
        bars += Strings.repeat('|', total)
        if (progress < 1)
            bars += Strings.repeat('|', max - total)
        val components = mutableListOf<Component>()
        components.add(Component.literal(bars.substring(0, total)).withStyle{it.withColor(lightColor)})
        components.add(Component.literal(bars.substring(total, max)).withStyle{it.withColor(darkColor)})
        var component: MutableComponent = Component.empty()
        for (c in components) {
            component = component.append(c)
        }
        return component
    }
}