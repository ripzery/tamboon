package com.ripzery.tamboon

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ripzery.tamboon.Network.ApiService
import com.ripzery.tamboon.data.Tamboon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit private var mCharityListAdapter: CharityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCharityListAdapter = CharityListAdapter(mutableListOf(), {
            val intent = Intent(this, DonateActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("name", it.name)
            intent.putExtra("logo_url", it.logo)
            startActivity(intent)
        })
        recyclerView.adapter = mCharityListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ApiService.mTamboonApiClient.getCharities().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            Log.d("MainActivity", it.toString())
            mCharityListAdapter.setList(it)
        }, {
            it.printStackTrace()
        })
    }

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
