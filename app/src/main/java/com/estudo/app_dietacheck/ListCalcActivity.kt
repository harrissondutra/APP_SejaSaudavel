package com.estudo.app_dietacheck

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estudo.app_dietacheck.databinding.ActivityListCalcBinding
import com.estudo.app_dietacheck.models.Calc
import com.google.android.material.appbar.MaterialToolbar
import java.util.Locale

class ListCalcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListCalcBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListCalcBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar)
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setLogo(null)
        setSupportActionBar(toolbar)

        val txtToolbar = findViewById<TextView>(R.id.txt_toolbar)
        txtToolbar.text = getString(R.string.list_all)

        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        recyclerView = binding.rvListImc
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }.start()
    }

    private inner class ListCalcAdapter(
        private val calcList: List<Calc>
    ) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListCalcViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = calcList[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int = calcList.size


        inner class ListCalcViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            fun bind(item: Calc) {
                val tv = itemView as TextView

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val data = sdf.format(item.createdDate)
                val res = item.res

                tv.text = getString(R.string.list_response, res, data)


            }

        }
    }
}