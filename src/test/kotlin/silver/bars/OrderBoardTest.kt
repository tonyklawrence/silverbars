package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Buy
import silver.bars.domain.Direction.Sell
import silver.bars.domain.OrderId
import silver.bars.domain.Summary.OrderSummary
import silver.bars.util.SomeOrderIds

class OrderBoardTest {
    private val orders = OrderBoard(SomeOrderIds("foo", "bar", "baz", "qux"))

    @Test fun `a new order board has no orders`() {
        val (buyOrders, sellOrders) = orders.summary()
        assertThat(buyOrders, isEmpty)
        assertThat(sellOrders, isEmpty)
    }

    @Test fun `can register an order and generate an id`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        assertThat(orderId, equalTo(OrderId("foo")))
        assertThat(orders.summary().sellOrders, hasElement(OrderSummary(3.5, 306)))
        assertThat(orders.summary().buyOrders, isEmpty)
    }

    @Test fun `can cancel an existing order`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        orders.cancel(orderId)
        assertThat(orders.summary().sellOrders, isEmpty)
    }

    @Test fun `attempting to cancel a non-existing order should result in an error`() {
        assertThat(orders.cancel(OrderId("foo")), equalTo(OrderNotFound(OrderId("foo"))))
    }

    @Test fun `orders of the same value should be merged in the summary`() {
        orders.register("user1", 3.5, 306, Sell)
        orders.register("user2", 2.5, 306, Sell)
        assertThat(orders.summary().sellOrders, equalTo(listOf(OrderSummary(6.0, 306))))
    }

    @Test fun `buy and sell orders are not considered the same`() {
        orders.register("user1", 3.5, 306, Buy)
        orders.register("user2", 2.5, 306, Sell)

        val (buyOrders, sellOrders) = orders.summary()
        assertThat(buyOrders, equalTo(listOf(OrderSummary(3.5, 306))))
        assertThat(sellOrders, equalTo(listOf(OrderSummary(2.5, 306))))
    }

    @Test fun `sell orders with the lowest prices are shown first`() {
        orders.register("user1", 7.0, 200, Sell)
        orders.register("user2", 3.0, 100, Sell)
        orders.register("user3", 1.0, 300, Sell)

        assertThat(orders.summary().sellOrders, equalTo(listOf(
            OrderSummary(3.0, 100),
            OrderSummary(7.0, 200),
            OrderSummary(1.0, 300)
        )))
    }

    @Test fun `buy orders with the highest prices are shown first`() {
        orders.register("user1", 7.0, 200, Buy)
        orders.register("user2", 3.0, 100, Buy)
        orders.register("user3", 1.0, 300, Buy)

        assertThat(orders.summary().buyOrders, equalTo(listOf(
            OrderSummary(1.0, 300),
            OrderSummary(7.0, 200),
            OrderSummary(3.0, 100)
        )))
    }
}