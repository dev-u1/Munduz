package ulanapp.munduz.ui.fragments.basket

import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.ui.base.BaseView

interface BasketView : BaseView {

    fun showProducts(purchases: MutableList<PurchaseEntity>)

    fun purchaseAll(purchases: MutableList<PurchaseEntity>)

    fun showGoToHome()

    fun hidePurchaseButton()

    fun showPurchaseButton()

}