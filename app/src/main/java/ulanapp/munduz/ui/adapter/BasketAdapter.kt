package ulanapp.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.helpers.convertIntToString
import ulanapp.munduz.helpers.convertStringToInt
import ulanapp.munduz.helpers.setSmallImage
import ulanapp.munduz.interfaces.OnChangeSumListener
import ulanapp.munduz.interfaces.OnItemBasketClickListener


class BasketAdapter(
    private var context: Context,
    listener: OnItemBasketClickListener
) :
    RecyclerView.Adapter<BasketViewHolder>() {

    private var itemClickListener: OnItemBasketClickListener = listener

    private lateinit var sumChangeListener: OnChangeSumListener
    private lateinit var purchases: MutableList<PurchaseEntity>
    private lateinit var purchasesRepository: PurchasesRepository

    fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.purchases = purchases
    }

    fun setListener(listener: OnChangeSumListener) {
        this.sumChangeListener = listener
    }

    fun setRepository(repository: PurchasesRepository) {
        this.purchasesRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.basket_purchase_item, parent, false)
        return BasketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val purchase = purchases[position]
        holder.bind(purchase, itemClickListener)

        setSmallImage(context, purchase.picture.urlImage, holder.image)
        holder.name.text = purchase.name
        holder.price.text = purchase.priceInc.toString() + RUBLE
        holder.perPrice.text = purchase.perPriceInc

        val initialPrice = purchase.price
        val initialPerPrice = convertStringToInt(purchase.perPrice)
        var changedPerPrice = convertStringToInt(purchase.perPriceInc)
        var changedPrice = purchase.priceInc
        val perCount = convertIntToString(purchase.perPrice)

        holder.increase.setOnClickListener {
            changedPrice += initialPrice
            changedPerPrice += initialPerPrice
            purchase.priceInc = changedPrice
            purchase.perPriceInc = changedPerPrice.toString() + perCount

            updateValues(holder, purchase)
        }

        holder.decrease.setOnClickListener {
            if (changedPrice != purchase.price) {
                changedPrice -= initialPrice
                changedPerPrice -= initialPerPrice
                purchase.priceInc = changedPrice
                purchase.perPriceInc = changedPerPrice.toString() + perCount

                updateValues(holder, purchase)
            }
        }

        holder.remove.setOnClickListener {
            removePurchase(purchase, position, holder)
        }
    }

    private fun removePurchase(purchase: PurchaseEntity, position: Int, holder: BasketViewHolder) {
        if (purchasesRepository.isExist(purchase.id)) {
            purchasesRepository.remove(purchase.id)
            purchases.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, purchases.size)

            sumChangeListener.onAmountChanged(getAmountPurchases())
        }
    }

    private fun updateValues(holder: BasketViewHolder, purchase: PurchaseEntity) {
        purchasesRepository.update(purchase)
        sumChangeListener.onAmountChanged(getAmountPurchases())
        holder.price.text = purchase.priceInc.toString() + RUBLE
        holder.perPrice.text = purchase.perPriceInc
    }

    private fun getAmountPurchases(): Int {
        return purchasesRepository.purchasesAmount()
    }
}