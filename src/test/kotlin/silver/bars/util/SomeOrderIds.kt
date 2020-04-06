package silver.bars.util

import silver.bars.IdGenerator
import silver.bars.domain.OrderId

class SomeOrderIds(vararg ids: String): IdGenerator<OrderId> {
    private val orderIds = ids.map { OrderId(it) }.iterator()
    override fun newId(): OrderId = orderIds.next()
}