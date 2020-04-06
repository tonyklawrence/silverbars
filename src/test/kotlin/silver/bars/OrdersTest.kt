package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Sell
import silver.bars.domain.OrderId
import silver.bars.domain.OrderSummary

class OrdersTest {
    private val orders = OrderBoard(SomeOrderIds("foo", "bar", "baz", "qux"))

    @Test fun `a new order board has no orders`() {
        assertThat(orders.summary(), isEmpty)
    }

    @Test fun `can register an order and generate an id`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        assertThat(orderId, equalTo(OrderId("foo")))
        assertThat(orders.summary(), hasElement(OrderSummary(3.5, 306, Sell)))
    }

    @Test fun `can cancel an existing order`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        orders.cancel(orderId)
        assertThat(orders.summary(), isEmpty)
    }

    @Test fun `attempting to cancel a non-existing order should result in an error`() {
        assertThat(orders.cancel(OrderId("foo")), equalTo(OrderNotFound(OrderId("foo"))))
    }

    @Test fun `orders of the same value should be merged in the summary`() {
        orders.register("user1", 3.5, 306, Sell)
        orders.register("user2", 2.5, 306, Sell)
        assertThat(orders.summary(), equalTo(listOf(OrderSummary(6.0, 306, Sell))))
    }
}

class SomeOrderIds(vararg ids: String): IdGenerator<OrderId> {
    private val orderIds = ids.map { OrderId(it) }.iterator()
    override fun newId(): OrderId = orderIds.next()
}