package foundry.animamundi.content.brain.cortex.cortices.constants

import foundry.animamundi.content.brain.signal.EmotionSignal
import foundry.animamundi.content.brain.signal.NeuralSignal
import foundry.animamundi.content.brain.signal.NumericalSignal

class ActiveCortex: ConstantCortex(NeuralSignal.Type.NUMERICAL, { NumericalSignal(1.0) })