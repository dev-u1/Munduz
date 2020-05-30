package com.ulan.app.munduz.ui.purchase

import com.ulan.app.munduz.data.models.Message
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.helpers.SendEmailHelper
import javax.inject.Inject

class PurchasePresenterImpl : PurchasePresenter {

    private var view: PurchaseView
    private lateinit var sendEmailHelper: SendEmailHelper
    private lateinit var message: Message

    @Inject
    constructor(view: PurchaseView) {
        this.view = view
    }

    override fun setEmailHelper(emailHelper: SendEmailHelper) {
        this.sendEmailHelper = emailHelper
    }

    override fun putOrderToMessage(order: Order) {
        message = Message()
        message.email = "uulanerkinbaev@gmail.com"
        message.subject = "Приложение Munduz"
        message.body =
            "               Товары " + "\n" +
                    order.purchases + "\n" +
                    "> Сумма заказа  " + order.amountPurchases + "\n" +
                    "> Имя Клиента  " + order.clientName + "\n" +
                    "> Номер телефона  " + order.clientPhoneNumber + "\n" +
                    "> Способ покупки  " + order.purchaseMethod + "\n" +
                    "> Комментарий  " + order.comment + "\n"
    }

    override fun sendViaWhatsApp() {
        view.sendOrderToWhatsApp(message)
        sendWithEmailHelper()
        view?.closeDialog()
    }

    override fun sendViaEmail() {
        sendWithEmailHelper()
        view.showMessage("Ожидайте, вам позвонят в течении 15 минут")
    }

    private fun sendWithEmailHelper() {
        sendEmailHelper.setMessage(message)
        sendEmailHelper.execute()
    }

}