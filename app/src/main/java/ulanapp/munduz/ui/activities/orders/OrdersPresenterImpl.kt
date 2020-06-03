package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class OrdersPresenterImpl @Inject constructor() : BasePresenter<OrdersView>(), OrdersPresenter {

    private lateinit var purchases: MutableList<PurchaseEntity>

    override fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.purchases = purchases
    }

    override fun setPurchasesAmount(amount: Int) {
        val goods = "Итого " + purchases.size.toString() + " видов товара \n"
        val price = "К оплате " + amount.toString() + RUBLE
        getView()?.showTotalPurchases(goods + price)
    }


    override fun sendButtonClicked() {
        if (getView()?.isNotEmptyFields()!!) {
            getView()?.goToPurchaseMethod(getView()?.getInputOrder()!!)
        }
    }

    override fun cancelButtonClicked() {
        getView()?.cancelOrder()
    }

    override fun setToolbar() {
        getView()?.showToolbar()
    }

}