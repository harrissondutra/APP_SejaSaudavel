package com.estudo.app_dietacheck

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estudo.app_dietacheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.colorPrimary)
//        window.statusBarColor = Color.rgb(233, 30, 30)

        val mainItens = mutableListOf<MainItem>()
        getItens(mainItens)

        //Criar a RecyclerView
        val recyclerView = binding.recyclerView

        //Configurando o Adapter
        //Passando o id de forma funcional para o Adapter
        val adapter = MainAdapter(mainItens) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }

                2 -> {
                    checaClickId(id)
                    /* val intent = Intent(this, Activity::class.java)
                     startActivity(intent)*/
                }

                3 -> {
                    checaClickId(id)
                    /* val intent = Intent(this, PesoActivity::class.java)
                     startActivity(intent)*/
                }

                4 -> {
                    checaClickId(id)
                    /*val intent = Intent(this, AlturaActivity::class.java)
                    startActivity(intent)*/
                }
            }
        }
        recyclerView.adapter = adapter


        // Configurando o Layout
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        /*        binding.btnInicial.setOnClickListener {
                    val intent = Intent(this, ImcActivity::class.java)
                    startActivity(intent)
                }*/

    }

    private fun checaClickId(id: Int) {
        Toast.makeText(this, "Clicou no id: $id", Toast.LENGTH_SHORT).show()

    }

    private fun getItens(mainItens: MutableList<MainItem>) {
        mainItens.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.baseline_medical_services_24_red,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItens.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_man_4_24,
                textStringId = R.string.label_tmb,
                color = Color.YELLOW
            )
        )
        mainItens.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.baseline_medical_services_24,
                textStringId = R.string.principal_name,
                color = Color.RED
            )
        )
        mainItens.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.baseline_monitor_heart_24,
                textStringId = R.string.principal_name,
                color = Color.BLUE
            )
        )
        mainItens.add(
            MainItem(
                id = 5,
                drawableId = com.google.android.material.R.drawable.ic_mtrl_chip_close_circle,
                textStringId = R.string.principal_name,
                color = Color.MAGENTA
            )
        )
    }

    private inner class MainAdapter(
        private val mainItens: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit // Aqui passa o evento de clique em cada célula no construtor
        // De forna funcional, Não precisando criar uma interface
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual é o Layout XML da célula específica
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - Dispara toda vez que houver uma rolagem na tela e for necessário trocar o conteúdo
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItens[position]

            holder.bind(itemCurrent)
        }

        // 3 - Informa o tamanho da RecyclerView
        override fun getItemCount(): Int = mainItens.size

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_imc)
                val name: TextView = itemView.findViewById(R.id.item_txt_imc)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id) //INVOKE serve para chamar a função do construtor quando não utilizando uma interface
                }
            }
        }
    }


}