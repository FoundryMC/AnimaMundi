package foundry.animamundi.content.brain.cortex.cortices.constants

import foundry.animamundi.content.brain.signal.EmotionSignal
import foundry.animamundi.content.brain.signal.NeuralSignal

class RageCortex: ConstantCortex(NeuralSignal.Type.EMOTION, { EmotionSignal(0.0, 1.0, 0.0, 0.0) })