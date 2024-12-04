package utils

data class Vector2D(val x: Int, val y: Int) {
    companion object {
        object cardinal {
            // Basic cardinal directions
            val LR = Vector2D(1, 0)
            val RL = Vector2D(-1, 0)
            val TB = Vector2D(0, 1)
            val BT = Vector2D(0, -1)
            val all = listOf(LR,RL,TB,BT)
        }
        object diagonal {
            val TLBR = Vector2D(1,-1)
            val TRBL = Vector2D(-1,-1)
            val BLTR = Vector2D(1,1)
            val BRTL = Vector2D(-1,1)
            val all = listOf(TLBR,TRBL,BLTR,BRTL)
        }
        val allDirections = cardinal.all + diagonal.all
    }
}
