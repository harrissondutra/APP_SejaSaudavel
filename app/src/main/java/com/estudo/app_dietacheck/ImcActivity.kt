package com.estudo.app_dietacheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.estudo.app_dietacheck.databinding.ActivityImcBinding
import com.estudo.app_dietacheck.models.Calc
import com.google.android.material.appbar.MaterialToolbar


class ImcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImcBinding

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImcBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val toolbar: MaterialToolbar = findViewById(R.id.main_toolbar)
        toolbar.setTitle(getString(R.string.titleIMC))
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)



        toolbar.setNavigationOnClickListener {

        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_favorite -> {
                    Toast.makeText(this, "Arquivo Salvo", Toast.LENGTH_SHORT).show()

                    true
                }


                else -> false
            }

        }


        ///------------------////

        editWeight = binding.inputEdtWeight
        editHeight = binding.inputEdtHeight

        val btnCalculate: Button = binding.btnImcGenerate
        val materialToolbar: MaterialToolbar = findViewById(R.id.main_toolbar)
//        txtToolbar.text = getString(R.string.titleIMC)


        btnCalculate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.validate, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            val imcResponseId = imcResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // aqui vai rodar depois do click
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = result))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()

                }
                .create()
                .show()


            //Configurar para o teclado fechar
            //Services são usados para acessar serviços do sistema Ex: Teclado
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_more) {
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "imc")
        startActivity(intent)
    }

    @StringRes
    private fun imcResponse(result: Double): Int {
        return when {
            result <= 18.5 -> R.string.magreza
            result <= 24.9 -> R.string.normal
            result <= 29.99 -> R.string.sobrepeso
            result <= 39.99 -> R.string.obesidade
            else -> R.string.obesidade_grave
        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0"))
    }
}
