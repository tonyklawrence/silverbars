package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Sell
import silver.bars.domain.Summary.OrderSummary
import silver.bars.util.SomeOrderIds

class ExerciseAcceptanceTest {
    private val orders = OrderBoard(SomeOrderIds("foo", "bar", "baz", "qux"))

    @Test fun `example summary information is correctly calculated`() {
        orders.register("user1", 3.5, 306, Sell)
        orders.register("user2", 1.2, 310, Sell)
        orders.register("user3", 1.5, 307, Sell)
        orders.register("user4", 2.0, 306, Sell)

        val (buyOrders, sellOrders) = orders.summary()

        assertThat(buyOrders, isEmpty)
        assertThat(sellOrders, equalTo(listOf(
            OrderSummary(5.5, 306),
            OrderSummary(1.5, 307),
            OrderSummary(1.2, 310)
        )))
    }
}