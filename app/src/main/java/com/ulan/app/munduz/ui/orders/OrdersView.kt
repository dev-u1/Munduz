package com.ulan.app.munduz.ui.orders

import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.ui.base.BaseView

interface OrdersView : BaseView{

    fun showTotalPurchases(total: String)

    fun getInputOrder() : Order

    fun isNotEmptyFieldsDelivery() : Boolean

    fun cancelOrder()

    fun goToPurchaseMethod(order: Order)
    
}