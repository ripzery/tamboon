package com.ripzery.tamboon.pages.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ripzery.tamboon.R
import com.ripzery.tamboon.base.BaseMvpActivity
import com.ripzery.tamboon.data.Tamboon
import com.ripzery.tamboon.extensions.toast
import com.ripzery.tamboon.pages.donate.DonateActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
    override val mPresenter: MainContract.Presenter by lazy { MainPresenter() }
    lateinit private var mCharityListAdapter: CharityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbarCharity)
        supportActionBar?.title = "Tamboon"

        mCharityListAdapter = CharityListAdapter(mutableListOf(), { mPresenter.handleCharityClicked(it) })
        recyclerView.adapter = mCharityListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPresenter.loadCharitiesList()
    }

    // Override MVP method zone

    override fun hideLoading() {
        layoutLoading.visibility = View.GONE
    }

    override fun showCharitiesList(list: MutableList<Tamboon.Charity>) {
        mCharityListAdapter.setList(list)
    }

    override fun showDonateScreen(charity: Tamboon.Charity) {
        val intent = Intent(this, DonateActivity::class.java)
        with(charity) {
            intent.putExtra(DonateActivity.EXTRA_ID, id)
            intent.putExtra(DonateActivity.EXTRA_NAME, name)
            intent.putExtra(DonateActivity.EXTRA_LOGO_URL, logo)
        }
        startActivity(intent)
    }

    override fun showError(errorMsg: String) {
        tvError.visibility = View.VISIBLE
        toast(errorMsg)
    }

    // Inner class zone

    inner class CharityListAdapter(private var list: MutableList<Tamboon.Charity>, val callback: (Tamboon.Charity) -> Unit) : RecyclerView.Adapter<CharityListAdapter.CharityViewHolder>() {
        override fun onBindViewHolder(holder: CharityViewHolder?, position: Int) {
            holder?.setModel(list[position])
        }

        override fun getItemCount(): Int = list.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder = CharityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_charity, parent, false))

        fun setList(list: List<Tamboon.Charity>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }

        inner class CharityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val mName: TextView = itemView.findViewById(R.id.tvName)
            private val mLogo: ImageView = itemView.findViewById(R.id.ivImage)
            private val mCharityView: View = itemView.findViewById(R.id.linearLayout)
            private val mRequestOptions = RequestOptions.circleCropTransform().centerCrop().error(R.mipmap.ic_launcher_round).fallback(R.mipmap.ic_launcher_round)

            fun setModel(charity: Tamboon.Charity) {
                mName.text = charity.name
                Glide.with(this@MainActivity).load(charity.logo).apply(mRequestOptions).into(mLogo)
                mCharityView.setOnClickListener { callback(list[adapterPosition]) }
            }
        }
    }
}
