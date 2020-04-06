package silver.bars.domain

data class Summary(val buyOrders: List<OrderSummary>, val sellOrders: List<OrderSummary>) {
    data class OrderSummary(val quantity: Double, val price: Int)
}