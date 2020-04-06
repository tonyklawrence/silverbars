package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Sell
import silver.bars.domain.Order
import silver.bars.domain.OrderId

class OrdersTest {
    private val orders = OrdersBoard(SomeOrderIds("foo", "bar", "baz", "qux"))

    @Test fun `a new order board has no orders`() {
        assertThat(orders.summary(), isEmpty)
    }

    @Test fun `can register an order`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        assertThat(orderId, equalTo(OrderId("foo")))
        assertThat(orders.summary(), hasElement(Order(orderId, "user1", 3.5, 306, Sell)))
    }

    @Test fun `can cancel an existing order`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        orders.cancel(orderId)
        assertThat(orders.summary(), isEmpty)
    }
}

class SomeOrderIds(vararg ids: String): IdGenerator<OrderId> {
    private val orderIds = ids.map { OrderId(it) }.iterator()
    override fun newId(): OrderId = orderIds.next()
}