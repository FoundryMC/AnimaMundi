package foundry.animamundi.content.brain.cortex.cortices.constants

import foundry.animamundi.content.brain.signal.EmotionSignal
import foundry.animamundi.content.brain.signal.NeuralSignal

class PositiveCortex: ConstantCortex(NeuralSignal.Type.EMOTION, { EmotionSignal(1.0, 0.0, 0.0, 0.0) })