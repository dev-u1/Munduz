package com.ulan.app.munduz.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.more.sections.ContactUsFragment
import com.ulan.app.munduz.ui.more.sections.WtiteToUsFragment
import kotlinx.android.synthetic.main.more_layout.*
import javax.inject.Inject

class MoreFragment: BaseFragment(), MoreView{

    @Inject
    lateinit var mPresenter: MorePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.more_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()

        contacts_us.setOnClickListener{
            mPresenter.goToContactsUs()
        }

        write_tous_button.setOnClickListener{
            mPresenter.goToWriteToUsFragment()
        }

        instagram.setOnClickListener{
            mPresenter.instagramClicked()
        }

        facebook.setOnClickListener{
            mPresenter.facebookClicked()
        }

        odnoklassniki.setOnClickListener{
            mPresenter.odnoklassnikiClicked()
        }
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.more)
    }

    override fun showContactsUs() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, ContactUsFragment())
            .commit()
    }

    override fun showLangChange() {
        TODO("not implemented")
    }

    override fun showWriteUs() {
        val dialogFragment = WtiteToUsFragment()
        dialogFragment.show(activity!!.supportFragmentManager, "dialogg")
    }

    override fun showInstagramPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/munduz.ru"))
        startActivity(intent)
    }

    override fun showOdnoklassnikiPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/profile/581176986653"))
        startActivity(intent)
    }

    override fun showFacebookPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/profile.php?id=100008205792318"))
        startActivity(intent)
    }

    companion object{
        fun newInstance(): MoreFragment {
            val args: Bundle = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

}